INSERT INTO Categories(name, color) VALUES ('Take out Food', '#FF7D62')
INSERT INTO Categories(name, color) VALUES ('Gas & Transportation', '#EADE20')
INSERT INTO Categories(name, color) VALUES ('Groceries', '#941CDD')
INSERT INTO Categories(name, color) VALUES ('Home Expenses', '#F87DE4')
INSERT INTO Categories(name, color) VALUES ('Miscelanius', '#264BE3')
INSERT INTO Categories(name, color) VALUES ('Rent', '#E36926')
INSERT INTO Categories(name, color) VALUES ('Savings', '#3CE326')
INSERT INTO Categories(name, color) VALUES ('Services', '#4BDCDB')

INSERT INTO Monthly_Incomes(upper_income_limit, active) VALUES (1000000, true)

INSERT INTO Monthly_Budgets(begin_date, end_date, title) VALUES ('2020-10-01', '2021-01-31', 'Non-Fixed Expenses')
INSERT INTO Monthly_Budgets(begin_date, title) VALUES ('2020-12-01', 'Fixed Expenses')

INSERT INTO Monthly_Budget_Categories(monthly_limit, category_id, monthly_budget_id) VALUES(200000, 1, 1)
INSERT INTO Monthly_Budget_Categories(monthly_limit, category_id, monthly_budget_id) VALUES(200000, 2, 1)
INSERT INTO Monthly_Budget_Categories(monthly_limit, category_id, monthly_budget_id) VALUES(200000, 3, 1)
INSERT INTO Monthly_Budget_Categories(monthly_limit, category_id, monthly_budget_id) VALUES(100000, 4, 2)
