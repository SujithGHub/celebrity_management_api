package com.example.celebrity_management.repository;

import com.example.celebrity_management.model.Celebrity;
import com.example.celebrity_management.util.Types;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CelebrityRepository extends JpaRepository<Celebrity, String> {

  List<Celebrity> findByUsersId(String id);

  Celebrity findByMailIdAndPhoneNumber(String mailId, String phoneNumber);

    @Query("SELECT DISTINCT c FROM Celebrity c JOIN c.categories cat " +
    "WHERE (:searchTerm IS NULL OR (:searchTerm IS NOT NULL " +
    "AND (LOWER(c.name) LIKE %:searchTerm% OR LOWER(cat.name) LIKE %:searchTerm%)))")
    List<Celebrity> findBySearchTerm(@Param("searchTerm") String searchTerm);

    Page<Celebrity> findAllByStatus(Types.Status status,Pageable pageable);

    @Query(value = "SELECT DISTINCT c FROM Celebrity c JOIN c.categories cat " +
    "WHERE LOWER(c.name) LIKE %:searchTerm% OR LOWER(cat.name) LIKE %:searchTerm% " +
    "AND c.status = :status")
    Page<Celebrity> findBySearchTermAndStatus(@Param("searchTerm") String searchTerm, 
                                              @Param("status") Types.Status status, 
                                              Pageable pageable);


      List<Celebrity> findByCategories_IdAndTopics_Id(String categoryId, String topicId);
      List<Celebrity> findByCategories_Id(String categoryId);
      List<Celebrity> findByTopics_Id(String topicId);

      @Query("SELECT c FROM Celebrity c JOIN c.categories cat JOIN c.topics t WHERE cat.id = :categoryId AND t.id IN :topicIds")
      List<Celebrity> findByCategories_IdAndTopics_IdIn(@Param("categoryId") String categoryId, @Param("topicIds") List<String> topicIds);

  
}
