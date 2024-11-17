package vn.hoidanit.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.hoidanit.jobhunter.entity.Conversation;
 
public interface ConversationRepository extends JpaRepository<Conversation, Long>  {

}
