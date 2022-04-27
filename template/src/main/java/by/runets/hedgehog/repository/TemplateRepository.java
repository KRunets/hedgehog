package by.runets.hedgehog.repository;

import by.runets.hedgehog.domain.Template;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TemplateRepository extends CrudRepository<Template, UUID> {
    @Query("FROM template WHERE slug=?1")
    Template readTemplateBySlug(String slug);
}
