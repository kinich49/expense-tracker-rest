INSERT INTO Categories(name, color) VALUES ('Take out Food', '#FF7D62')
INSERT INTO Categories(name, color) VALUES ('Gas & Transportation', '#EADE20')
INSERT INTO Categories(name, color) VALUES ('Groceries', '#941CDD')
INSERT INTO Categories(name, color) VALUES ('Home Expenses', '#F87DE4')
INSERT INTO Categories(name, color) VALUES ('Miscelanius', '#264BE3')
INSERT INTO Categories(name, color) VALUES ('Rent', '#E36926')
INSERT INTO Categories(name, color) VALUES ('Savings', '#3CE326')
INSERT INTO Categories(name, color) VALUES ('Services', '#4BDCDB')

INSERT INTO Monthly_Incomes(upper_income_limit, begin_date) VALUES (1000000, '2020-10-01')
INSERT INTO Monthly_Incomes(upper_income_limit, begin_date, end_date) VALUES (800000, '2020-01-01', '2020-06-30')
INSERT INTO Monthly_Incomes(upper_income_limit, begin_date, end_date) VALUES (900000, '2020-07-01', '2020-09-30')

INSERT INTO Monthly_Budgets(begin_date, end_date, title, base_limit) VALUES ('2020-10-01', '2021-01-31', 'Non-Fixed Expenses', 0)
INSERT INTO Monthly_Budgets(begin_date, title, base_limit) VALUES ('2020-12-01', 'Fixed Expenses', 0)

INSERT INTO Monthly_Budget_Categories(monthly_limit, category_id, monthly_budget_id) VALUES(200000, 1, 1)
INSERT INTO Monthly_Budget_Categories(monthly_limit, category_id, monthly_budget_id) VALUES(200000, 2, 1)
INSERT INTO Monthly_Budget_Categories(monthly_limit, category_id, monthly_budget_id) VALUES(200000, 3, 1)
INSERT INTO Monthly_Budget_Categories(monthly_limit, category_id, monthly_budget_id) VALUES(100000, 4, 2)

INSERT INTO Payment_Methods(name) values('Credit Card RegularBank')
INSERT INTO Payment_Methods(name) values('Cash')

INSERT INTO Commercial_Establishments(name) values ('HEB')
INSERT INTO Commercial_Establishments(name) values ('Walmart')
INSERT INTO Commercial_Establishments(name) values ('Street Store')
INSERT INTO Commercial_Establishments(name) values ('Exxon Mobil')
INSERT INTO Commercial_Establishments(name) values ('Shin Long')
INSERT INTO Commercial_Establishments(name) values ('Carls Jr')

INSERT INTO Transactions(title, amount, transaction_date, category_id, payment_method_id, commercial_establishment_id) values ('Grocery Shopping', 150000, '2021-06-01T03:00:00', 3, 1, 1)

INSERT INTO Transactions(title, amount, transaction_date, category_id, payment_method_id, commercial_establishment_id) values ('Grocery Shopping', 150000, '2021-05-03T03:00:00', 3, 1, 1)
INSERT INTO Transactions(title, amount, transaction_date, category_id, payment_method_id, commercial_establishment_id) values ('Grocery Shopping', 90000, '2021-05-17T03:00:00', 3, 1, 1)

INSERT INTO Transactions(title, amount, transaction_date, category_id, payment_method_id, commercial_establishment_id) values ('Chinese Food', 20000, '2021-04-01T03:00:00', 1, 2, 5)
INSERT INTO Transactions(title, amount, transaction_date, category_id, payment_method_id, commercial_establishment_id) values ('Hamburgers with family', 30000, '2021-04-010T03:00:00', 1, 1, 6)
INSERT INTO Transactions(title, amount, transaction_date, category_id, payment_method_id, commercial_establishment_id) values ('Regular Gas', 75000, '2021-04-015T03:00:00', 2, 1, 4)
INSERT INTO Transactions(title, amount, transaction_date, category_id, payment_method_id, commercial_establishment_id) values ('Grocery Shopping', 90000, '2021-04-05T03:00:00', 3, 1, 2)
INSERT INTO Transactions(title, amount, transaction_date, category_id, payment_method_id, commercial_establishment_id) values ('Grocery Shopping', 210000, '2021-04-12T03:00:00', 3, 1, 2)
INSERT INTO Transactions(title, amount, transaction_date, category_id, payment_method_id, commercial_establishment_id) values ('Grocery Shopping', 150000, '2021-04-19T03:00:00', 3, 1, 2)