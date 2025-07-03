    package com.Buddewar.PracticeDemo.repository;

    import com.Buddewar.PracticeDemo.entity.Expense;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Modifying;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;
    import org.springframework.stereotype.Repository;
    import org.springframework.transaction.annotation.Transactional;

    import java.util.List;

    @Repository
    public interface ExpenseRepository extends JpaRepository<Expense,Integer> {

        List<Expense> findAllByUserId(int userId);

        @Query(
                value = "Select c.name as Category, sum(e.amount) as Total "+
                        "From Expenses e "+
                        "Join categories c On e.category_id=c.id "+
                        "where e.user_id=:userId "+
                        "group by c.name",
                nativeQuery = true
                // nativeQuery means table names should be referred from the database.
                /* Native SQL (aka raw SQL)
- Database-oriented: You write exact SQL as if you're in a SQL editor.
- Uses real table names and column names from your DB.
- Gives you full control, and lets you use database-specific features like LIMIT, JOIN USING, or even stored procedures.
*/
        )
        List<Object[]> getCategoryTotals(@Param("userId") int userId);
        /*
JPQL (Java Persistence Query Language)-Object-oriented: Think of it as SQL for Java entities, not raw tables.
Entity-based: You use entity class names and their fields, not table or column names.

*/


        @Query(
                value= "select p.name as PaymentMethod,sum(e.amount) as Total "+
                "from  expenses e "+
                "left join payment_methods p on p.id=e.payment_method_id "+
                "where user_id=:userId "+
                "group by e.payment_method_id",
                nativeQuery=true
        )
        List<Object[]> getPaymentTypeStats(@Param("userId") int userId);


        @Query(
                value="select month(expense_date) as Month, sum(amount) as amount "+
                        "from expenses " +
                        "where user_id=:userId "+
                        "group by month(expense_date) "+
                        "order by month(expense_date)",
                nativeQuery = true
        )
        List<Object[]> getMonthlyStats(@Param("userId") int userId);


        @Transactional
        @Modifying
        @Query(
                value = "update user_info set last_expense_date= ("+
                        "select max(expense_date) "+
                        "from expenses "+
                        "where user_id=:userId) "
                        +"where user_id=:userId",
                nativeQuery = true

        )
        public void saveLastExpenseDate(int userId);


    }
