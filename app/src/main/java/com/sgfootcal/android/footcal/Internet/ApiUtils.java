package com.sgfootcal.android.footcal.Internet;

/**
 * Created by serdar on 18.8.2017.
 */

public class ApiUtils {

    public static final String BASE_URL = "http://api.footcal.net/";


    public static LeaguesDaoInterface getLeaguesDaoInterface() {
        return RetrofitClient.getClient(BASE_URL).create(LeaguesDaoInterface.class);
    }
    public static TeamsDaoInterfae getTeamsDaoInterface() {
        return RetrofitClient.getClient(BASE_URL).create(TeamsDaoInterfae.class);
    }
    public static FixtureDaoInterface getFixtureDaoInterface() {
        return RetrofitClient.getClient(BASE_URL).create(FixtureDaoInterface.class);
    }
    public static FootballerDaoInterface getFootballerDaoInterface() {
        return RetrofitClient.getClient(BASE_URL).create(FootballerDaoInterface.class);
    }
    public static CoachDaoInterface getCoachDaoInterface() {
        return RetrofitClient.getClient(BASE_URL).create(CoachDaoInterface.class);
    }
    public static ClubChairmanDaoInterface getClubChairManDaoInterface() {
        return RetrofitClient.getClient(BASE_URL).create(ClubChairmanDaoInterface.class);
    }

}
