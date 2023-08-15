package com.challenge.challenge.repositories;

import com.challenge.challenge.models.TransactionsAudit;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionAuditRepository extends JpaRepository<TransactionsAudit, Id> {
}
