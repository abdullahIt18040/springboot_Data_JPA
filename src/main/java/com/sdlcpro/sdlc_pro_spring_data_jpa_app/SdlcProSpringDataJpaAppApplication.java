package com.sdlcpro.sdlc_pro_spring_data_jpa_app;

//import jakarta.persistence.EntityManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sdlcpro.sdlc_pro_spring_data_jpa_app.dto.ProductSearchDto;
import com.sdlcpro.sdlc_pro_spring_data_jpa_app.entity.Product;
import com.sdlcpro.sdlc_pro_spring_data_jpa_app.entity.Student;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

//@SpringBootApplication
public class SdlcProSpringDataJpaAppApplication  {
    final  static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("postgres_unit");
    public static void main(String[] args) {
//        SpringApplication.run(SdlcProSpringDataJpaAppApplication.class, args);
        Student student = new Student(102L,"abdullahease","abdsdullah@gmail.com","1234sdf56");

    var filter = new ProductSearchDto(
            "laptop",
            "laptop",
            2000,
            5000,
            List.of("dell","hp")
    );

        transactinal((em)->{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Product> cq = cb.createQuery(Product.class);
            Root<Product> root = cq.from(Product.class);

            var list = new ArrayList<Predicate>();

            if(filter.searchValue() !=null && filter.searchValue().isBlank())
            {

                var searchValue = filter.searchValue().trim();
                var namePredicate = cb.like(root.get("name"),"%"+searchValue+"%");
                var desPredicate = cb.like(root.get("des"),"%"+searchValue+"%");
                list.add((Predicate) cb.or(namePredicate,desPredicate));
            }

            if(filter.category() !=null)
            {
                list.add((Predicate) cb.equal(root.get("category"),filter.category()));
            }

            if(filter.minPrice()<= filter.maxprice())
            {
                var minPredicate = cb.greaterThanOrEqualTo(root.get("price"),filter.minPrice());
                var maxPredicate = cb.lessThanOrEqualTo(root.get("price"),filter.minPrice());
                list.add((Predicate) cb.and(minPredicate,maxPredicate));
            }

            if(filter.brands() != null && !filter.brands().isEmpty())
            {
            list.add((Predicate) root.get("brand").in(filter.brands()))   ;
            }

            cq.where(list.toArray(new jakarta.persistence.criteria.Predicate[0]));
            cq.orderBy(cb.desc(root.get("id")));

     var q= em.createQuery(cq);
     q.getResultStream().forEach(System.out::println);


        });




//        Student student = new Student(1,"abdullah","abdullah@gmail.com","123456");
//        em.persist(student);
    }


    static void transactinal(Consumer<EntityManager> consumer) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin(); // Start the transaction
            consumer.accept(em); // Execute the provided logic
            transaction.commit(); // Commit if successful
        } catch (Exception ex) {
            if (transaction.isActive()) {
                transaction.rollback(); // Rollback in case of error
            }
            ex.printStackTrace(); // Log the exception
            throw new RuntimeException("Transaction failed: " + ex.getMessage(), ex);
        } finally {
            if (em.isOpen()) {
                em.close(); // Ensure EntityManager is closed
            }
        }
    }


}
