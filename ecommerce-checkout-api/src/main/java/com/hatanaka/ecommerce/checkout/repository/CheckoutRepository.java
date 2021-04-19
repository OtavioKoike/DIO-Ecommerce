package com.hatanaka.ecommerce.checkout.repository;

import com.hatanaka.ecommerce.checkout.entity.CheckoutEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//Acessar a entidade
//Responsavel por realmente acessar o banco de dados
@Repository
//Diz quem e a entidade que vai usar e o tipo de id
public interface CheckoutRepository extends JpaRepository<CheckoutEntity, Long> {

//  Criando uma busca por codigo
    Optional<CheckoutEntity> findByCode(String code);
}
