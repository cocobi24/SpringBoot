package pirate.piratespring.repository;

import org.springframework.jdbc.datasource.DataSourceUtils;
import pirate.piratespring.domain.Holidays;
import pirate.piratespring.domain.Member;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository {

    private final DataSource dataSource;

    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Member save(Member member) {
        String sql = "insert into member(name, owner, description, address, phone) values(?,  ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getOwner());
            pstmt.setString(3, member.getDescription());
            pstmt.setString(4, member.getAddress());
            pstmt.setString(5, member.getPhone());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                member.setLevel(rs.getLong(1));
            } else {
                throw new SQLException("level 조회 실패");
            }
            return member;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Member delete(Member member) {
        String sql = "DELETE FROM member WHERE level = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, member.getLevel());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            return member;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Holidays holidayCreate(Holidays holiday) {
        String sql = "insert into holidays(level, holiday) values(?,  ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, holiday.getLevel());
            pstmt.setString(2, holiday.getHoliday());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            return holiday;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findByLevel(Long level) {
        String sql = "select * from member where level = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, level);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Member member = new Member();
                member.setName(rs.getString("name"));
                member.setOwner(rs.getString("owner"));
                member.setDescription(rs.getString("description"));
                member.setLevel(rs.getLong("level"));
                member.setAddress(rs.getString("address"));
                member.setPhone(rs.getString("phone"));
                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from member order by level";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Member> members = new ArrayList<>();
            while(rs.next()) {
                Member member = new Member();
                member.setName(rs.getString("name"));
                member.setOwner(rs.getString("owner"));
                member.setDescription(rs.getString("description"));
                member.setLevel(rs.getLong("level"));
                member.setAddress(rs.getString("address"));
                member.setPhone(rs.getString("phone"));
                members.add(member);
            }
            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Member member = new Member();
                member.setName(rs.getString("name"));
                member.setOwner(rs.getString("owner"));
                member.setDescription(rs.getString("description"));
                member.setLevel(rs.getLong("level"));
                member.setAddress(rs.getString("address"));
                member.setPhone(rs.getString("phone"));
                return Optional.of(member);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}