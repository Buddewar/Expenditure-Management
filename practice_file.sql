SELECT * FROM expenditure_management.expenses;

select month(expense_date), sum(amount) from expenditure_management.expenses
where user_id=4
group by month(expense_date)
order by month(expense_date);

select c.name ,sum(e.amount) as overall_savings from expenditure_management.expenses e
left join categories c on e.category_id=c.id 
where c.name='Savings & Investing' group by c.id;

select p.name,sum(e.amount) from  expenditure_management.expenses e
left join payment_methods p on p.id=e.payment_method_id
where user_id=4
group by e.payment_method_id;

Select c.name as Category, sum(e.amount) as Total
From Expenses e 
Join categories c On e.category_id=c.id 
where e.user_id=4
group by c.name;
