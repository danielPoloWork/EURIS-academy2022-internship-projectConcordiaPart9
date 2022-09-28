package com.euris.academy2022.concordia.jpaRepositories;

import com.euris.academy2022.concordia.dataPersistences.dataModels.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, String> {

    

}
