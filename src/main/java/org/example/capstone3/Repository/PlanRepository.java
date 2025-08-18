package org.example.capstone3.Repository;

import org.example.capstone3.Model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//Mohammed
@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer> {

    Plan findPlanById(Integer id);
}
