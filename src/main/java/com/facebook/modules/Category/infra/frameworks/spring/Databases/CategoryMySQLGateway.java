package com.facebook.modules.Category.infra.frameworks.spring.Databases;


import com.facebook.modules.Category.domain.Category;
import com.facebook.modules.Category.domain.CategoryGateway;
import com.facebook.modules.Category.domain.CategoryID;
import com.facebook.modules.Category.infra.CategoryDataEntity;
import com.facebook.modules.Category.infra.frameworks.spring.CategoryRepository;
import com.facebook.modules.Flup.system.required.helpers.GatewayDefault;
import com.facebook.modules.Flup.system.required.pagination.Pagination;
import com.facebook.modules.Flup.system.required.pagination.SearchQuery;
import com.facebook.modules.Flup.system.utils.RabbitMQUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import java.util.stream.StreamSupport;

import static com.facebook.modules.Flup.spring.utils.SpecificationUtils.like;

@Component
public class CategoryMySQLGateway extends GatewayDefault<CategoryDataEntity> implements CategoryGateway {

    private final CategoryRepository repository;

    public CategoryMySQLGateway(final CategoryRepository repository) {
        this.repository = repository;
    }

    // Simular confirmação do pedido
    private static String confirmOrder(String orderId) throws IOException {
        return "Pedido #" + orderId + " confirmado. Preparando pizza...";
    }

    // Simular entrega da pizza
    private static String deliverPizza(String orderId) throws IOException {
        return "Entrega do pedido #" + orderId + " realizada com sucesso!";
    }

    // Mover mensagem para fila de erro
    private static String moveToErrorQueue(String orderId) throws IOException {
        return "Erro na entrega do pedido #" + orderId;
    }

    @Override
    public Category create(Category aCategory) throws IOException, TimeoutException {
        try {

            String queueName = "categories";
            String exchangeName = "amq.direct";


            RabbitMQUtils.Builder rabbitMQUtils = new RabbitMQUtils
                    .Builder("localhost", 5672, "guest", "guest")
                    .connect().createQueue(queueName).bindQueueToExchange(queueName, exchangeName)
                    .send(queueName, confirmOrder("123"))
                    .close();

            return save(aCategory);

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        return aCategory;
    }

    @Override
    public void deleteById(CategoryID anID) {
        final String idValue = anID.getValue();
        if (this.repository.existsById(idValue)) {
            this.repository.deleteById(idValue);
        }
    }

    @Override
    public Optional<Category> findById(CategoryID anID) {
        return this.repository.findById(anID.getValue()).map(CategoryDataEntity::toAggregate);
    }

    @Override
    public Category update(final Category aCategory) {
        return save(aCategory);
    }

    @Override
    public Pagination<Category> findAll(final SearchQuery aQuery) {

        final var specifications = Optional.ofNullable(aQuery.terms())
                .filter(str -> !str.isBlank())
                .map(this::assembleSpecification)
                .orElse(null);


        final var pageResult =
                this.repository.findAll(Specification.where(specifications), page(aQuery));

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(CategoryDataEntity::toAggregate).toList()
        );
    }

    @Override
    public List<CategoryID> existsByIds(final Iterable<CategoryID> categoryIDs) {
        final var ids = StreamSupport.stream(categoryIDs.spliterator(), false)
                .map(CategoryID::getValue)
                .toList();
        return this.repository.existsByIds(ids).stream()
                .map(CategoryID::from)
                .toList();
    }

    public Category save(final Category aCategory) {
        return this.repository.save(CategoryDataEntity.from(aCategory)).toAggregate();
    }

    private Specification<CategoryDataEntity> assembleSpecification(final String str) {

        final Specification<CategoryDataEntity> nameLike = like("name", str);

        return nameLike;

    }
}
