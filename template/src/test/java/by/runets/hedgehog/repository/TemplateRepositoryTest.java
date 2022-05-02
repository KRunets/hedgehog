package by.runets.hedgehog.repository;

import by.runets.hedgehog.domain.Template;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TemplateRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private TemplateRepository templateRepository;

    @Test
    public void testReadTemplateBySlug() {
        // given
        Template expected = new Template.TemplateBuilder()
                .body("Test body")
                .slug("test_slug")
                .build();

        entityManager.persist(expected);
        entityManager.flush();

        // when
        final Template actual = templateRepository.readTemplateBySlug("test_slug");

        // then
        Assert.assertNotNull(actual);
        Assert.assertEquals(actual.getBody(), "Test body");
    }

}
