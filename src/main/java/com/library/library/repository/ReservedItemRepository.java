package com.library.library.repository;

import com.library.library.model.ReservedItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.library.library.model.security.User;

import java.util.List;

public interface ReservedItemRepository extends CrudRepository<ReservedItem,Long> {
//public interface ReservedItemRepository extends JpaRepository<ReservedItem,Long> {
    ReservedItem findByBookId(Long id);
    List<ReservedItem> findAll();
    List<ReservedItem> findByUser(User user);

    @Modifying
    @Transactional(rollbackOn = Exception.class) //jakarta transactional vs ?????
    @Query(nativeQuery = true, value = "DELETE FROM reserved_item WHERE id IN(?1)")
    void deleteCollected(List<Long> idList);
    /*
    @Modifying
    @Query("delete from ReservedItem t where t.reserved_date = ?1 and user_personal_data_id = ?2 and collected = true")
    void deleteAllCollected(Date now, Long id);
*/
}
