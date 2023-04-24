player_list
===
    select * FROM [RYPlatformManagerDB].[dbo].[Exchange_review]
    where userId = #{userId}
    @if(!isEmpty(StartTime)){
         and CONVERT(VARCHAR(100), createTime, 23) >= CONVERT(VARCHAR(100), #{StartTime}, 23)
    @}
    @if(!isEmpty(EndTime)){
         and CONVERT(VARCHAR(100), createTime, 23) <= CONVERT(VARCHAR(100), #{EndTime}, 23)
    @}
     order by createTime desc