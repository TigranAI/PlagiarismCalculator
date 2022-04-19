package ru.tigran.PlagiarismCalculator.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tigran.PlagiarismCalculator.database.entity.SolutionData;

import java.util.UUID;

@Repository
public interface SolutionDataRepository extends JpaRepository<SolutionData, UUID> {
}