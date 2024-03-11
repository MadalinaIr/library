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
    /*
    @Query(value = " select b1.id, b1.book_id, b1.borowed_date, b1.due_date, b1.return_date, b1.returned, b1.user_personal_data_id\n" +
                   " from borowed_item b1 left join book\n" +
                  "            on b1.book_id=book.id where book.title like %?1% ",
    public Page<BorowedItem> findAll(String keyword, Pageable pageable);
     */
    //public Page<BorowedItem> findDistinctByBookTitleLikeOrBookAuthorsFirstNameLikeOrBookAuthorsLastNameLike(String title, String firstName, String lastName, Pageable pageable);
    //List<BorowedItem> findByUserPersonalData(UserPersonalData userPersonalData);
    List<BorowedItem> findByUserAndReturnedEquals(User user, Boolean returned);
    public Page<BorowedItem> findDistinctByBookTitleLikeOrBookAuthorsNameLike(String title, String name, Pageable pageable);
    public Page<BorowedItem> findAllByUserIdAndBookTitleLikeOrBookAuthorsNameLike(Long userId, String title, String name, Pageable pageable);
  //  public Page<BorowedItem> findByBookTitleLike(String title, Pageable pageable); OK

    @Query(value = "SELECT b from borowed_item b where b.dueDate = ?1 ", nativeQuery = true)
    public List<BorowedItem> findByDueDate(String dueDate);
   // List<BorowedItem> findById(Long id);

    public Page<BorowedItem> findAllByUser(Pageable pageable, User user);


}
