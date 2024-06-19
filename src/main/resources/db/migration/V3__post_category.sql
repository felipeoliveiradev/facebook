CREATE TABLE posts_categories (
    post_id VARCHAR(36) NOT NULL,
    category_id VARCHAR(36) NOT NULL,
    CONSTRAINT idx_post_category UNIQUE(post_id, category_id),
    CONSTRAINT fk_post_id FOREIGN KEY (post_id) REFERENCES post (id) ON DELETE CASCADE,
    CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE CASCADE
);