INSERT INTO Categories(title, color) VALUES ('Take out Food', '#FF7D62') 
INSERT INTO Categories(title, color) VALUES ('Gas & Transportation', '#EADE20') 
INSERT INTO Categories(title, color) VALUES ('Groceries', '#941CDD')
INSERT INTO Categories(title, color) VALUES ('Home Expenses', '#F87DE4')
INSERT INTO Categories(title, color) VALUES ('Miscelanius', '#264BE3') 
INSERT INTO Categories(title, color) VALUES ('Rent', '#E36926')
INSERT INTO Categories(title, color) VALUES ('Savings', '#3CE326')
INSERT INTO Categories(title, color) VALUES ('Services', '#4BDCDB')

INSERT INTO Transaction_Items(title, amount, date_created, category_id) VALUES ('Tacos', 10000, '2019-12-11', 1)
INSERT INTO Transaction_Items(title, amount, date_created, category_id) VALUES ('Gas', 76000, '2019-12-7', 2)
INSERT INTO Transaction_Items(title, amount, date_created, category_id) VALUES ('Netflix', 22900, '2019-12-10', 6)
INSERT INTO Transaction_Items(title, amount, date_created, category_id) VALUES ('December-January', 250000, '2019-12-01', 6)
INSERT INTO Transaction_Items(title, amount, date_created, category_id) VALUES ('Bonds', 100000, '2019-12-15', 7)
INSERT INTO Transaction_Items(title, amount, date_created, memo, category_id) VALUES ('Plumber', 20000, '2019-11-30', 'Kitchen fauce had a leak', 4)
INSERT INTO Transaction_Items(title, amount, date_created, memo, category_id) VALUES ('Amazon MX', 21900, '2019-10-12', 'Smart Light bulb', 5)
INSERT INTO Transaction_Items(title, amount, date_created, category_id) VALUES ('Little Caesars', 12900, '2019-11-15', 1)
INSERT INTO Transaction_Items(title, amount, date_created, memo, category_id) VALUES ('Medicine', 5000, '2019-12-02', 'Paracetamol, Loratadina', 5)
INSERT INTO Transaction_Items(title, amount, date_created, memo, category_id) VALUES ('HEB', 149583, '2019-12-04', 'vegetables(brocoli, carrots), fruit(apples, pineapple, watermelon), bathroom(shampoo, razors, toilet paper), clothes(winter clothes, socks), meat(beef, fish, chicken breasts)', 3)

INSERT INTO Monthly_Budgets(title) VALUES ('Default Budget')

INSERT INTO Monthly_Budget_Categories(category_id, monthly_budget_id, monthly_limit, local_date) VALUES(1, 1, 1000, '2019-12-01')
INSERT INTO Monthly_Budget_Categories(category_id, monthly_budget_id, monthly_limit, local_date) VALUES(2, 1, 2000, '2019-12-01')
INSERT INTO Monthly_Budget_Categories(category_id, monthly_budget_id, monthly_limit, local_date) VALUES(3, 1, 3000, '2019-12-01')