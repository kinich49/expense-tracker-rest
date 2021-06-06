INSERT INTO Categories(name, color) VALUES ('Take out Food', '#FF7D62')
INSERT INTO Categories(name, color) VALUES ('Gas & Transportation', '#EADE20')
INSERT INTO Categories(name, color) VALUES ('Groceries', '#941CDD')
INSERT INTO Categories(name, color) VALUES ('Home Expenses', '#F87DE4')
INSERT INTO Categories(name, color) VALUES ('Miscelanius', '#264BE3')
INSERT INTO Categories(name, color) VALUES ('Rent', '#E36926')
INSERT INTO Categories(name, color) VALUES ('Savings', '#3CE326')
INSERT INTO Categories(name, color) VALUES ('Services', '#4BDCDB')

INSERT INTO Monthly_Incomes(upper_income_limit, begin_date) VALUES (1000000, '2020-12-01')

INSERT INTO Monthly_Budgets(begin_date, title, base_limit) VALUES ('2020-12-01', 'Non-Fixed Expenses', 250000)
INSERT INTO Monthly_Budgets(begin_date, title, base_limit) VALUES ('2020-12-01', 'Fixed Expenses', 0)

INSERT INTO Monthly_Budget_Categories(monthly_limit, category_id, monthly_budget_id) VALUES(100000, 4, 2)

INSERT INTO Payment_Methods(name) values('Credit Card RegularBank')
INSERT INTO Payment_Methods(name) values('Cash')

INSERT INTO Commercial_Establishments(name) values ('HEB')
INSERT INTO Commercial_Establishments(name) values ('Walmart')
INSERT INTO Commercial_Establishments(name) values ('Street Store')

