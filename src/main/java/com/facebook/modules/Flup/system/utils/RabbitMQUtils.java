package com.facebook.modules.Flup.system.utils;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQUtils {

    private final String queue = "_QUEUE";
    private final ConnectionFactory factory;
    private Connection connection;
    private Channel channel;

    private RabbitMQUtils(String host, int port, String username, String password) {
        factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
    }

    public static RabbitMQUtils connect(String host, int port, String username, String password) throws IOException, TimeoutException {
        RabbitMQUtils rabbitMQUtils = new RabbitMQUtils(host, port, username, password);
        rabbitMQUtils.connection = rabbitMQUtils.factory.newConnection();
        rabbitMQUtils.channel = rabbitMQUtils.connection.createChannel();
        return rabbitMQUtils;
    }

    public void connect() throws IOException, TimeoutException {
        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    public void send(String queueName, String message) throws IOException {
        channel.basicPublish("", queueName + queue, null, message.getBytes());
    }

    public String receive(String queueName) throws IOException {
        GetResponse response = channel.basicGet(queueName + queue, true);
        if (response != null) {
            return new String(response.getBody(), "UTF-8");
        }
        return null;
    }

    public void createQueue(String queueName) throws IOException {
        channel.queueDeclare(queueName + queue, false, false, false, null);
    }

    public void deleteQueue(String queueName) throws IOException {
        channel.queueDelete(queueName + queue);
    }

    public void createExchange(String exchangeName, BuiltinExchangeType exchangeType) throws IOException {
        channel.exchangeDeclare(exchangeName, exchangeType);
    }

    public void deleteExchange(String exchangeName) throws IOException {
        channel.exchangeDelete(exchangeName);
    }

    public void bindQueueToExchange(String queueName, String exchangeName) throws IOException {
        channel.queueBind(queueName + queue, exchangeName, queueName + "_" + exchangeName + "_KEY");
    }

    public void unbindQueueFromExchange(String queueName, String exchangeName) throws IOException {
        channel.queueUnbind(queueName + queue, exchangeName, queueName + "_" + exchangeName + "_KEY");
    }

    public void consumeMessages(String queueName, MessageHandler messageHandler) throws IOException {
        channel.basicConsume(queueName + queue, true, createDeliverCallback(messageHandler), consumerTag -> {
        });
    }

    public void startRealtimeQueueMonitoring(String queueName, MessageHandler messageHandler, Integer timeTread) {
        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    GetResponse response = channel.basicGet(queueName + queue, true);
                    if (response != null) {
                        String message = new String(response.getBody(), "UTF-8");
                        messageHandler.handleMessage(message);
                    }
                    Thread.sleep(timeTread);
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void declareExchange(String exchangeName, BuiltinExchangeType exchangeType) throws IOException {
        channel.exchangeDeclare(exchangeName, exchangeType);
    }

    public void close() throws IOException, TimeoutException {
        if (channel != null && channel.isOpen()) {
            channel.close();
        }
        if (connection != null && connection.isOpen()) {
            connection.close();
        }
    }

    private DeliverCallback createDeliverCallback(MessageHandler messageHandler) {
        return (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            messageHandler.handleMessage(message);
        };
    }

    public interface MessageHandler {
        void handleMessage(String message);
    }

    public static class Builder {
        private final RabbitMQUtils rabbitMQUtils;

        public Builder(String host, int port, String username, String password) {
            rabbitMQUtils = new RabbitMQUtils(host, port, username, password);
        }

        public Builder connect() throws IOException, TimeoutException {
            rabbitMQUtils.connect();
            return this;
        }

        public Builder send(String queueName, String message) throws IOException {
            rabbitMQUtils.send(queueName, message);
            return this;
        }

        public Builder createQueue(String queueName) throws IOException {
            rabbitMQUtils.createQueue(queueName);
            return this;
        }

        public Builder deleteQueue(String queueName) throws IOException {
            rabbitMQUtils.deleteQueue(queueName);
            return this;
        }

        public Builder createExchange(String exchangeName, BuiltinExchangeType exchangeType) throws IOException {
            rabbitMQUtils.createExchange(exchangeName, exchangeType);
            return this;
        }

        public Builder deleteExchange(String exchangeName) throws IOException {
            rabbitMQUtils.deleteExchange(exchangeName);
            return this;
        }

        public Builder bindQueueToExchange(String queueName, String exchangeName) throws IOException {
            rabbitMQUtils.bindQueueToExchange(queueName, exchangeName);
            return this;
        }

        public Builder unbindQueueFromExchange(String queueName, String exchangeName) throws IOException {
            rabbitMQUtils.unbindQueueFromExchange(queueName, exchangeName);
            return this;
        }

        public Builder consumeMessages(String queueName, MessageHandler messageHandler) throws IOException {
            rabbitMQUtils.consumeMessages(queueName, messageHandler);
            return this;
        }

        public Builder startRealtimeQueueMonitoring(String queueName, MessageHandler messageHandler, Integer timeTread) throws IOException {
            rabbitMQUtils.startRealtimeQueueMonitoring(queueName, messageHandler, timeTread);
            return this;
        }

        public Builder declareExchange(String exchangeName, BuiltinExchangeType exchangeType) throws IOException {
            rabbitMQUtils.declareExchange(exchangeName, exchangeType);
            return this;
        }

        public Builder close() throws IOException, TimeoutException {
            rabbitMQUtils.close();
            return this;
        }

        public Builder createDeliverCallback(RabbitMQUtils.MessageHandler messageHandler) throws IOException, TimeoutException {
            rabbitMQUtils.createDeliverCallback(messageHandler);
            return this;
        }

        public RabbitMQUtils build() {
            return rabbitMQUtils;
        }
    }
}
