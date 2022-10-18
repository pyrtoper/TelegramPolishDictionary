package com.pyrtoper.dictionary.repository;

import com.pyrtoper.dictionary.entity.WorkStates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkStateRepository extends JpaRepository<WorkStates, Long> {

}
