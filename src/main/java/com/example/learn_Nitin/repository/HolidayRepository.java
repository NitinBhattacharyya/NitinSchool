package com.example.learn_Nitin.repository;

import com.example.learn_Nitin.model.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//public class HolidayRepository {
//    private final JdbcTemplate jdbcTemplate;
//    @Autowired
//    public HolidayRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//    public List<Holiday> findAllHolidays()
//    {
//        String sql="SELECT * FROM holidays";
//        var rowMapper= BeanPropertyRowMapper.newInstance(Holiday.class);
//        //we can create one more row mapper for holidays like ContactRowMapper
//        //But when field names and column names inside model class and inside the table are exactly matching
//        //then we don't need write our own custom row mapper
//        //We can use the above statement to create a row mapper
//        return jdbcTemplate.query(sql,rowMapper);
//    }
//}
//The string beside Holiday is the return type of the primary key column
public interface HolidayRepository extends CrudRepository<Holiday,String> {
}
