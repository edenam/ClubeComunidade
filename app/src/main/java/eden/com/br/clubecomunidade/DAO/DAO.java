package eden.com.br.clubecomunidade.DAO;

import android.content.Context;

/**
 * <p>This class has the pourpose of providing an abstraction layer for all the data
 * access throughtout the project. </p>
 * <p>The class will hold references for every kind of data access methods that might
 * be needed on the project.</p>
 * <p>Right now, the possible data sources are: Parse Objects, Web Services
 * and Local DataStore.</p>
 * <p>The classes that access data sources must be included in the same package and
 * have "default" access modifiers.</p>
 *
 *
 * @author  Eden Meireles
 * @see     eden.com.br.clubecomunidade.DAO.ParseDAO
 * @see     eden.com.br.clubecomunidade.DAO.LocalDatastoreDAO
 * @see     eden.com.br.clubecomunidade.DAO.WebDAO
 * @since   0.3
*/



public class DAO {
    private static DAO ourInstance = new DAO();

    public static DAO getInstance() {
        return ourInstance;
    }

    private DAO() { }

    public void ParseDAOInitialize(Context context) {
        ParseDAO.getInstance().initialize(context);
    }

    public void getNewsForHomeBanner(int count, final DAOAccessCallback callback){
        ParseDAO.getInstance().getNewsForHomeBanner(count, callback);
    }

    public void getEventsWithPics(int count, final DAOAccessCallback callback){
        ParseDAO.getInstance().getEventsWithPics(count, callback);
    }

    public void getSimpleGuideList(int count, final DAOAccessCallback callback){
        ParseDAO.getInstance().getSimpleGuideList(count, callback);
    }
}
