package com.library.library.repository;

import com.library.library.model.BorowedItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import com.library.library.model.security.User;

//@Transactional
//public interface BorowedItemRepository extends CrudRepository<BorowedItem, Long>, PagingAndSortingRepository<BorowedItem, Long>, JpaSpecificationExecutor {
public interface BorowedItemRepository extends CrudRepository<BorowedItem, Long>, PagingAndSortingRepository<BorowedItem, Long> {

    //public Page<BorowedItem> findDistinctByBookTitleLikeOrBookAuthorsFirstNameLikeOrBookAuthorsLastNameLike(String title, String firstName, String lastName, Pageable pageable);
    List<BorowedItem> findByUserAndReturnedEquals(User user, Boolean returned);
    public Page<BorowedItem> findDistinctByBookTitleLikeOrBookAuthorsNameLike(String title, String name, Pageable pageable);
    public Page<BorowedItem> findAllByUserIdAndBookTitleLikeOrBookAuthorsNameLike(Long userId, String title, String name, Pageable pageable);
  //  public Page<BorowedItem> findByBookTitleLike(String title, Pageable pageable); OK

    @Query(value = "SELECT b from borowed_item b where b.dueDate = ?1 ", nativeQuery = true)
    public List<BorowedItem> findByDueDate(String dueDate);
   // List<BorowedItem> findById(Long id);

    public Page<BorowedItem> findAllByUser(Pageable pageable, User user);


}
