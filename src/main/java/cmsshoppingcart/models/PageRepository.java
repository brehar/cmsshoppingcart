package cmsshoppingcart.models;

import cmsshoppingcart.models.data.Page;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageRepository extends JpaRepository<Page, Integer> {
  List<Page> findAllByOrderBySortingAsc();
  Page findBySlug(String slug);
  Page findBySlugAndIdNot(String slug, int id);
}
