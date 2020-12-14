INSERT INTO Categories(name, color) VALUES ('Take out Food', '#FF7D62')
INSERT INTO Categories(name, color) VALUES ('Gas & Transportation', '#EADE20')
INSERT INTO Categories(name, color) VALUES ('Groceries', '#941CDD')
INSERT INTO Categories(name, color) VALUES ('Home Expenses', '#F87DE4')
INSERT INTO Categories(name, color) VALUES ('Miscelanius', '#264BE3')
INSERT INTO Categories(name, color) VALUES ('Rent', '#E36926')
INSERT INTO Categories(name, color) VALUES ('Savings', '#3CE326')
INSERT INTO Categories(name, color) VALUES ('Services', '#4BDCDB')

INSERT INTO Monthly_Budgets(budget_date, monthly_limit) VALUES ('2020-12-01', 3600000)

INSERT INTO Monthly_Budget_Categories(monthly_limit, category_id, monthly_budget_id) VALUES(200000, 1, 1)
INSERT INTO Monthly_Budget_Categories(monthly_limit, category_id, monthly_budget_id) VALUES(200000, 2, 1)
INSERT INTO Monthly_Budget_Categories(monthly_limit, category_id, monthly_budget_id) VALUES(200000, 3, 1)
