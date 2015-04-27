package eden.com.br.clubecomunidade.DAO;

import java.util.List;

/**
 * Created by root on 26/04/15.
 */
public interface DAOAccessCallback {
    void done(List<?> resultSet, Exception e);
}
