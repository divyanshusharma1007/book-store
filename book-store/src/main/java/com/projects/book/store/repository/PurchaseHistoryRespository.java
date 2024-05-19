package com.projects.book.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.book.store.model.PurchaseHistory;

@Repository
public interface PurchaseHistoryRespository extends JpaRepository<PurchaseHistory, Long> {

}
