list
===
select * from RecordDrawInfo 

register_list
===
SELECT CONVERT(VARCHAR(100), RegisterDate, 23) RegisterDate, COUNT(*) RegisterTotalNum
  FROM [RYAccountsDB].[dbo].[AccountsInfo] group by CONVERT(VARCHAR(100), RegisterDate, 23)