package com.example.learn_Nitin.repository;

import com.example.learn_Nitin.constants.NitinSchoolConstants;
import com.example.learn_Nitin.model.Contact;
import com.example.learn_Nitin.rowmappers.ContactRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
//public class ContactRepository {
//    private final JdbcTemplate jdbcTemplate;
//    @Autowired
//    public ContactRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//    public int saveContactMsg(Contact contact)
//    {
//        String sql="INSERT INTO contact_msg(name,mobile_num,email,subject,message,status,created_at,created_by)VALUES(?,?,?,?,?,?,?,?)";
//        return jdbcTemplate.update(sql,contact.getName(),contact.getMobileNum(),contact.getEmail(),contact.getSubject(),contact.getMessage(),
//                contact.getStatus(),contact.getCreatedAt(),contact.getCreatedBy());
//        //returns the number of rows that got inserted
//    }
//
//    public List<Contact> findMsgsWithStatus(String open) {
//        String sql="SELECT * FROM contact_msg WHERE status=?";
//        return jdbcTemplate.query(sql, new PreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps) throws SQLException {
//                ps.setString(1,open);
//            }
//        },new ContactRowMapper());
//    }
//
//    public int updateMsgStatus(int contactId, String status, String upatedBy) {
//        String sql="UPDATE contact_msg SET status=?,updated_by=?,updated_at=? WHERE contact_id=?";
//        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps) throws SQLException {
//                ps.setString(1,status);
//                ps.setString(2,upatedBy);
//                ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
//                ps.setInt(4,contactId);
//            }
//        });
//    }
//}
public interface ContactRepository extends CrudRepository<Contact,Integer>, PagingAndSortingRepository<Contact,Integer> {
    //Indicates to Spring JPA,there is a field called status inside POJO class
    //And that field status corresponds to a column called status inside the table
    //so fetch records based on the status value given
    List<Contact> findByStatus(String status);

    Page<Contact> findByStatus(String status, Pageable pageable);
}
