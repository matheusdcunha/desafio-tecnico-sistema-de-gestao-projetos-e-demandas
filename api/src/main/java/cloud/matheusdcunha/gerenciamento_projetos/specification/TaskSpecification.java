package cloud.matheusdcunha.gerenciamento_projetos.specification;

import cloud.matheusdcunha.gerenciamento_projetos.criteria.TaskFilterCriteria;
import cloud.matheusdcunha.gerenciamento_projetos.domain.Task;
import cloud.matheusdcunha.gerenciamento_projetos.domain.enums.TaskPriority;
import cloud.matheusdcunha.gerenciamento_projetos.domain.enums.TaskStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class TaskSpecification {

    public static Specification<Task> build(TaskFilterCriteria criteria) {

        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(!ObjectUtils.isEmpty(criteria.status())) {
                predicates.add(builder.equal(root.get("status"), criteria.status()));
            }

            if(!ObjectUtils.isEmpty(criteria.priority())) {
                predicates.add(builder.equal(root.get("priority"), criteria.priority()));
            }

            if(!ObjectUtils.isEmpty(criteria.projectId())) {
                predicates.add(builder.equal(root.join("project").get("id"), criteria.projectId()));
            }

            if(!ObjectUtils.isEmpty(criteria.projectName())) {
                predicates.add(builder.like(root.join("project").get("name"), "%" + criteria.projectName() + "%" ));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
