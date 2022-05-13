package ru.shanalotte.bankbarrel.serviceregistry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shanalotte.bankbarrel.serviceregistry.dto.RegisteredServiceInfo;

@Repository
public interface RegisteredServiceRepository extends JpaRepository<RegisteredServiceInfo, String> {

  RegisteredServiceInfo findByName(String serviceName);
}
