package br.com.volvo.persistence.repositories;

import br.com.volvo.persistence.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
}
