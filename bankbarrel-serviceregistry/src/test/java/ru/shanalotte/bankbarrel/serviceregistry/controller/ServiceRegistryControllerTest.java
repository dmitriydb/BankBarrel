package ru.shanalotte.bankbarrel.serviceregistry.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import ru.shanalotte.bankbarrel.serviceregistry.dto.RegisteredServiceInfo;
import ru.shanalotte.bankbarrel.serviceregistry.dto.ServiceDeletionDto;
import ru.shanalotte.bankbarrel.serviceregistry.repository.RegisteredServiceRepository;

public class ServiceRegistryControllerTest {
  RegisteredServiceInfo registeredServiceInfo = new RegisteredServiceInfo();
  {
    registeredServiceInfo.setHost("host");
    registeredServiceInfo.setPort("port");
    registeredServiceInfo.setName("name");
  }

  RegisteredServiceRepository registeredServiceRepository = mock(RegisteredServiceRepository.class);
  ServiceRegistryController serviceRegistryController = new ServiceRegistryController(registeredServiceRepository);


  @Test
  public void tryAddService() throws JsonProcessingException {
    serviceRegistryController.createService(registeredServiceInfo);
    verify(registeredServiceRepository).save(registeredServiceInfo);
  }

  @Test
  public void tryUpdateServiceInfo() throws JsonProcessingException {
    serviceRegistryController.updateService(registeredServiceInfo);
    verify(registeredServiceRepository).save(registeredServiceInfo);
  }

  @Test
  public void tryDeleteService() throws JsonProcessingException {
    RegisteredServiceInfo registeredServiceInfo = new RegisteredServiceInfo();
    registeredServiceInfo.setName("blab");
    when(registeredServiceRepository.findByName(any())).thenReturn(registeredServiceInfo);
    ServiceDeletionDto dto = new ServiceDeletionDto();
    String serviceName = "fdsgdfs";
    dto.setName(serviceName);
    serviceRegistryController.deleteService(dto);
    verify(registeredServiceRepository).findByName(serviceName);
    verify(registeredServiceRepository).deleteById(serviceName);
  }

  @Test
  public void tryFindService() throws JsonProcessingException {
    RegisteredServiceInfo registeredServiceInfo = new RegisteredServiceInfo();
    registeredServiceInfo.setName("agjdk");
    serviceRegistryController.createService(registeredServiceInfo);
    serviceRegistryController.getServiceInfo(registeredServiceInfo.getName());
    verify(registeredServiceRepository).findByName(registeredServiceInfo.getName());
  }


}