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

    @Query("SELECT c FROM Celebrity c JOIN c.categories cat WHERE cat.id = :categoryId")
    List<Celebrity> findByCategoryId(@Param("categoryId") String categoryId);
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

}
