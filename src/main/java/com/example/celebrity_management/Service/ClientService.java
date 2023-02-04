package com.example.celebrity_management.Service;

import com.example.celebrity_management.model.ClientModel;
import com.example.celebrity_management.repository.ClientRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

  @Autowired
  private ClientRepository clientRepository;

  public ClientModel create(ClientModel clientModel) {
    return clientRepository.save(clientModel);
  }

  public List<ClientModel> getAll() {
    return clientRepository.findAll();
  }

  public Optional<ClientModel> getById(String id) {
    return clientRepository.findById(id);
  }

  public List<ClientModel> delete(String id) {
    clientRepository.deleteById(id);
    return getAll();
  }
}
