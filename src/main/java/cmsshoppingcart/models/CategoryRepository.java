package cmsshoppingcart.models;

import cmsshoppingcart.models.data.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {}
