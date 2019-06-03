package utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.List;


public class NetInfoUtil {
    public static Socket ss = null;
    public static DataInputStream din = null;
    public static DataOutputStream dos = null;
    public static String message = "";
    public static byte[] data;

    public static void connect() throws Exception {
        ss = new Socket("192.168.1.205", 10009);
        din = new DataInputStream(ss.getInputStream());
        dos = new DataOutputStream(ss.getOutputStream());
    }

    public static void disConnect() {
        if (dos!= null) {
            try {
                dos.flush();
                dos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (din != null) {
            try {
                din.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (ss != null) {
            try {
                ss.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static String getusername(String content){

        try{
            connect();
            dos.writeUTF(Constant.GetUserName
                    +MyConverter.escape(content));
            message=din.readUTF();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            disConnect();
        }
        return MyConverter.unescape(message);
    }
    public static String[] getuserfriendname(String content){
        try{
            connect();
            dos.writeUTF(Constant.GetUserFriendName
                    +MyConverter.escape(content));
            message=din.readUTF();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            disConnect();
        }
        return StrListChange.StrToArray(MyConverter.unescape(message));
    }
    public static String[] getuserfriendpen(String content)
    {
        try{
            connect();
            dos.writeUTF(Constant.GetUserFriendPen+MyConverter.escape(content));
            message=din.readUTF();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            disConnect();
        }
        return StrListChange.StrToArray(MyConverter.unescape(message));
    }

    public static String[] getuserfriendphoto(String content)
    {
        try{
            connect();
            dos.writeUTF(Constant.GetUserFriendPhoto+MyConverter.escape(content));
            message=din.readUTF();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            disConnect();
        }
        return StrListChange.StrToArray(MyConverter.unescape(message));
    }

    public static byte[] getPicture(String picname)
    {
        try{
            connect();
            dos.writeUTF(Constant.GetOnePicture+MyConverter.escape(picname));
            data=IOUtil.readBytes(din);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            disConnect();
        }
        return data;
    }

    public static String[] getuserfriend(String content)
    {
        try{
            connect();
            dos.writeUTF(Constant.GetUserFriend+MyConverter.escape(content));
            message=din.readUTF();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            disConnect();
        }
        return StrListChange.StrToArray(MyConverter.unescape(message));
    }

    public static String[] getuserfriendid(String content)
    {
        try{
            connect();
            dos.writeUTF(Constant.GetUserFriendId+MyConverter.escape(content));
            message=din.readUTF();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            disConnect();
        }
        return StrListChange.StrToArray(MyConverter.unescape(message));
    }

    public static String[] getuserjointeam(String content)
    {
        try{
            connect();

            dos.writeUTF(Constant.GetUserJoinTeam+MyConverter.escape(content));
            message=din.readUTF();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            disConnect();
        }
        return StrListChange.StrToArray(MyConverter.unescape(message));
    }

    public static String getuseronephoto(String content)
    {
        try{
            connect();

            dos.writeUTF(Constant.GetUserOnePhoto+MyConverter.escape(content));
            message=din.readUTF();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            disConnect();
        }
        return MyConverter.unescape(message);
    }

    public static  String login(String[] info)
    {
        try{
            connect();
            dos.writeUTF(Constant.LOGIN+MyConverter.escape(StrListChange.ArrayToStrAndroid(info)));
            message=din.readUTF();
        }
        catch(Exception e){
            e.printStackTrace();

        }
        finally{
            disConnect();
        }
        return MyConverter.unescape(message);
    }

    public static String register(String info[])
    {
        try{
            connect();
            dos.writeUTF(Constant.REGISTER+MyConverter.escape(StrListChange.ArrayToStrAndroid(info)));
            message=din.readUTF();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            disConnect();
        }
        return MyConverter.unescape(message);
    }

    public static String getToken(String userInfo)
    {
        try{
            connect();
            dos.writeUTF(Constant.GetToken+MyConverter.escape(userInfo));
            message=din.readUTF();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            disConnect();
        }
        return MyConverter.unescape(message);
    }

    public static String getuserId(String info)
    {
        try{
            connect();
            dos.writeUTF(Constant.GETUSERID+MyConverter.escape(info));
            message=din.readUTF();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            disConnect();
        }
        return MyConverter.unescape(message);
    }

    public static String insertuser(String info)
    {
        try{
            connect();
            dos.writeUTF(Constant.REGISTER+MyConverter.escape(info));
            message=din.readUTF();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            disConnect();
        }
        return MyConverter.unescape(message);
    }
    public static String[] getoneHuodong() {
        try {
            connect();
            dos.writeUTF(Constant.GetAllHuodong);
            message = din.readUTF();
        } catch (Exception e) {
            System.out.println("dfgdfgh");
            e.printStackTrace();
        } finally {
            disConnect();
        }
        return StrListChange.StrToArray(MyConverter.unescape(message));
    }
    public static List<String[]> gethuodongtime()
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GetAllHuodongTime );
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> gethuodongplace()
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GetAllHuodongPlace );
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static String[] gethuodongid()
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GetAllHuodongId );
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToArray(MyConverter.unescape(message));
    }
    public static String gethuodongdetail(String content)
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GetAllHuodngDetail + MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return MyConverter.unescape(message);
    }
    public static String getonehuodongtime(String content)
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GetOneHuodongTime + MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return MyConverter.unescape(message);
    }
    public static String getonehuodongplace(String content)
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GetOneHuodongPlace + MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return MyConverter.unescape(message);
    }
    public static List<String[]> getallhuodongpicture()
    {
        try {
            connect();
            dos.writeUTF(Constant.GetAllHuodongPicture);
            message = din.readUTF();
        } catch (Exception e) {
            System.out.println("dfgdfgh");
            e.printStackTrace();
        } finally {
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getallhuadongpicture()
    {
        try {
            connect();
            dos.writeUTF(Constant.GetAllHuadongPicture);
            message = din.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    /*public static byte[] getPicture(String content) {
        try {
            connect();
            dos.writeUTF(Constant.GET_Picture + MyConverter.escape(content));
            data = IOUtil.readBytes(din);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
        return data;
    }*/
    public static List<String[]> gethuodongname()
    {
        try {
            connect();
            dos.writeUTF(Constant.GetHuodongName);
            message = din.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> gethuodongbyname(String content)//4gethuodongbyname
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GetOneHuodongByName + MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static String getoneshetuanimagebyname(String content)//7getoneshetuanimagebyname
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GetOneHuodongByName + MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return MyConverter.unescape(message);
    }
    public static List<String[]> gethuodongtimebyname(String content)//1gethuodongtimebyname
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GetOneHuodongTimeByName + MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> gethuodongplacebyname(String content)//2gethuodongplacebyname
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GetOneHuodongPlaceByName + MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> gethuodongidbyname(String content)//3gethuodongidbyname
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GetOneHuodongIdByName+ MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getonehuodongimagebyname(String content)
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GetOneHuodongPictureByName+ MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> gethuodongpinglun(String content)
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GetHuodongPinglun+ MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static void inserthuodongpinglun(String content)
    {
        try{
            connect();
            dos.writeUTF(Constant.InsertHuodongPinglun+MyConverter.escape(content));
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
    }
    public static String[] getallshetuan()
    {
        try {
            connect();
            dos.writeUTF(Constant.GetAllShetuan);
            message = din.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
        return StrListChange.StrToArray(MyConverter.unescape(message));
    }
    public static String[] getallshetuanid()
    {
        try {
            connect();
            dos.writeUTF(Constant.GetAllShetuanId);
            message = din.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
        return StrListChange.StrToArray(MyConverter.unescape(message));
    }
    public static String getshetuandetail(String content)
    {
        try{
            connect();
            dos.writeUTF(Constant.GetShetuanDetail+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return MyConverter.unescape(message);
    }
    public static String getshetuanpeople(String content)
    {
        try{
            connect();
            dos.writeUTF(Constant.GetShetuanPeople+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return MyConverter.unescape(message);
    }
    public static List<String[]> getusermessagebyid(String id) {
        try {
            connect();
            dos.writeUTF(Constant.Getusermessagebyid + id);
            message = din.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static void setusermessagebyid(String mes) {
        try {
            connect();
            dos.writeUTF(Constant.Setusermessagebyid + MyConverter.escape(mes));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
    }
    // ͨ���û���õ��û�����
    public static String getuserpassword(String thisusername) {
        try {
            connect();
            dos.writeUTF(Constant.Getuserpassword + thisusername);
            message = din.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
        return MyConverter.unescape(message);
    }
    public static void updateandusermessage(String mes) {
        try {
            connect();
            dos.writeUTF(Constant.Updateandusermessage
                    + MyConverter.escape(mes));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
    }
    public static List<String[]> getpasswordandphone(String message) {
        try {
            connect();
            dos.writeUTF(Constant.Getpasswordandphone
                    + MyConverter.escape(message));
            message = din.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static void feedback(String message) {
        try {
            connect();
            dos.writeUTF(Constant.Feedback + MyConverter.escape(message));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
    }
    public static String getUserPhoto(String name) {
        try {
            connect();
            dos.writeUTF(Constant.GetUserPhoto + name);
            message = din.readUTF();
            String ceshi = new String();
            ceshi = MyConverter.unescape(message);
            System.out.println(ceshi);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
        return MyConverter.unescape(message);
    }
    // ��ȡͼƬ(���ͼƬ��)
    public static byte[] getscPicture(String picname) {
        try {
            connect();
            dos.writeUTF(Constant.GET_SCPicture + MyConverter.escape(picname));
            data = IOUtil.readBytes(din);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
        return data;
    }
    public static String getuserstates(String thisusername) {
        // TODO Auto-generated method stub
        try {
            connect();
            dos.writeUTF(Constant.Getuserstates + thisusername);
            message = din.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
        return MyConverter.unescape(message);
    }
    public static List<String[]> getuserlist(String mes) {
        try {
            connect();
            dos.writeUTF(Constant.Getuserlist + MyConverter.escape(mes));
            message = din.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> gettiyushetuan()
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GetTiyuShetuan);
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> gettiyushetuanid()
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GetTiyuShetuanId);
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getwenyishetuan()
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GetWenyiShetuan);
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getwenyishetuanid()
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GetWenyiShetuanId);
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getcishanshetuan()
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GetCishanShetuan);
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getcishanshetuanid()
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GetCishanShetuanId);
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getxueshushetuan()
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GetXueshuShetuan);
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getxueshushetuanid()
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GetXueshuShetuanId);
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getshetuankouhao()
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GetShetuanKouhao);
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getshetuanpicture()
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GetAllShetuanPicture);
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }

    public static List<String[]> getoneshetuanpciture()
    {
        try{
            connect();
            dos.writeUTF(Constant.GetOneShetuanPicture);
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }

    public static String[] getshetuanxiangce(String content)
    {
        try{
            connect();
            dos.writeUTF(Constant.GetShetuanxiangce+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToArray(MyConverter.unescape(message));
    }

    public static List<String[]> getshetuantiyupicture()
    {
        try{
            connect();
            dos.writeUTF(Constant.GetShetuantiyupicture);
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getshetuantiyukouhao()
    {
        try{
            connect();
            dos.writeUTF(Constant.GetShetuantiyukouhao);
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getshetuantiyupicture2()
    {
        try{
            connect();
            dos.writeUTF(Constant.GetShetuantiyupicture2);
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getshetuanxueshupicture()
    {
        try{
            connect();
            dos.writeUTF(Constant.GetShetuanXueshupicture);
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getshuetuanxueshupciture2()
    {
        try{
            connect();
            dos.writeUTF(Constant.GetShetuanXueshupciture2);
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getshuetuanxueshukouhao()
    {
        try{
            connect();
            dos.writeUTF(Constant.GetShetuanXueshukouhao);
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getshetuancishankouhao()
    {
        try{
            connect();
            dos.writeUTF(Constant.GetShetuanCishankouhao);
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getshetuancishanpicture()
    {
        try{
            connect();
            dos.writeUTF(Constant.GetShetuanCishanpicture);
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getshetuancishanpicture2()
    {
        try{
            connect();
            dos.writeUTF(Constant.GetShetuanCishanpicture2);
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getshetuankouhaobyname(String content)
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GetShetuanKouhaoByName+ MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getshetuanpicturebyname(String content)
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GetShetuanPictureByName+ MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getshetuanpicture2byname(String content)
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GetShetuanPicture2ByName+ MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getshetuanbyname(String content)
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GetShetuanByName+ MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getshetuanidbyname(String content)
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GetShetuanIdByName+ MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> gethuodongpinglunpicture(String content)
    {
        try{
            connect();
            dos.writeUTF(Constant.GetHuodongPinglunPicture+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> gethuodongpinglunname(String content)
    {
        try{
            connect();
            dos.writeUTF(Constant.GetHuodongPinglunName+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> gethuodongpinglunid(String content)
    {
        try{
            connect();
            dos.writeUTF(Constant.GetHuodongPinglunId+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static void deletehuodongpinglun(String content)
    {
        try{
            connect();
            dos.writeUTF(Constant.DeleteHuodongPinglun+MyConverter.escape(content));
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
    }
    public static List<String[]> getshetuanmessage(){
        try{
            connect();
            dos.writeUTF(Constant.GetShetuanMessage);
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getallhuodongmessage(){
        try{
            connect();
            dos.writeUTF(Constant.GetAllHuodongMessage);
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getwenyishetuankouhao(){
        try{
            connect();
            dos.writeUTF(Constant.GetShetuanWenyikouhao);
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getwenyishetuanpicture(){
        try{
            connect();
            dos.writeUTF(Constant.GetShetuanWenyipicture);
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getwenyishetuanpciture2(){
        try{
            connect();
            dos.writeUTF(Constant.GetShetuanWenyipicture2);
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static void insertpic(byte[] data,String info)
    {
        try
        {
            connect();
            dos.writeUTF(Constant.InsertPic+MyConverter.escape(info));
            dos.writeInt(data.length);
            dos.write(data);
            dos.flush();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            disConnect();
        }
    }
    public static List<String[]> gethuodongfenbu(String content)
    {
        try{
            connect();
            dos.writeUTF(Constant.GetHuodongFenbu+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> gethuodongmessagebyname(String content)
    {
        try{
            connect();
            dos.writeUTF(Constant.GetHuodongMessageByName+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static void inserthuodongmessage(String mes) {
        try {
            connect();
            dos.writeUTF(Constant.InsertHuodongMessage + MyConverter.escape(mes));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
    }
    public static List<String[]> getshetuanchengyuan(String content)
    {
        try {
            connect();
            dos.writeUTF(Constant.getshetuanchengyuan+MyConverter.escape(content));
            message = din.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static void getFriend(String content)
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GETFRIEND+MyConverter.unescape(content));
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally
        {
            disConnect();
        }
    }
    //�������
    public static String insertFriend(String content)
    {
        try
        {
            connect();
            dos.writeUTF(Constant.INSERTFRIEND+MyConverter.unescape(content));
            message=din.readUTF();

        }catch(Exception e)
        {
            e.printStackTrace();
        }finally
        {
            disConnect();
        }
        return message;
    }
    //�����Լ���û���������  �����γɵ��ַ�
    public static String searchFriend(String content)
    {
        try
        {
            connect();
            dos.writeUTF(Constant.SEARCH+MyConverter.unescape(content));
            message=din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally
        {
            disConnect();
        }
        return message;
    }
    //�������ų�Ա��ID
    public static String[] getShetuanRenYunId(String content){
        try{
            connect();
            dos.writeUTF(Constant.GETSHETUANRENYUANID+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToArray(MyConverter.unescape(message));
    }
    //���ų�Ա������
    public static String[] getShetuanuserInfo(String content)
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GETSHETUANUSERINFO+MyConverter.escape(content));
            message=din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally
        {
            disConnect();
        }
        return StrListChange.StrToArray(MyConverter.unescape(message));
    }
    public static List<String[]> gethuodongtime2(String content)
    {
        try{
            connect();
            dos.writeUTF(Constant.GetHuodongPinglunTime+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static String getshetuandetailpicture(String content)
    {
        try{
            connect();
            dos.writeUTF(Constant.GetShetuanDetailPicture+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return MyConverter.unescape(message);
    }
    public static List<String[]> getyijianren(String content)
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GetYiJian+MyConverter.escape(content));
            message=din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally
        {
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static String getguanliid(String registerusername) {
        try {
            connect();
            dos.writeUTF(Constant.getguanliid
                    + MyConverter.escape(registerusername));
            message = din.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            disConnect();
        }
        return MyConverter.unescape(message);
    }
    public static void tianjiachenghyuan(String mes) {
        try {
            connect();
            dos.writeUTF(Constant.tianjiachenghyuan + MyConverter.escape(mes));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
    }
    public static void shanchuchengyuan(String mes) {
        try {
            connect();
            dos.writeUTF(Constant.shanchuchengyuan + MyConverter.escape(mes));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
    }
    public static List<String[]> getallshetuanchengyuan(String content)
    {
        try {
            connect();
            dos.writeUTF(Constant.getallshetuanchengyuan+MyConverter.escape(content));
            message = din.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getshetuan(String content)
    {
        try {
            connect();
            dos.writeUTF(Constant.getshetuan+MyConverter.escape(content));
            message = din.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getallguanlihuodong(String content)
    {
        try {
            connect();
            dos.writeUTF(Constant.getallguanlihuodong+MyConverter.escape(content));
            message = din.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static String getmaxid(String registerusername) {
        try {
            connect();
            dos.writeUTF(Constant.getmaxid
                    + MyConverter.escape(registerusername));
            message = din.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
        return MyConverter.unescape(message);
    }
    public static String getcommunityid(String registerusername) {
        try {
            connect();
            dos.writeUTF(Constant.getcommunityid
                    + MyConverter.escape(registerusername));
            message = din.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
        return MyConverter.unescape(message);
    }
    public static void inserthuodongm(String mes) {
        try {
            connect();
            dos.writeUTF(Constant.inserthuodongm + MyConverter.escape(mes));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
    }
    public static List<String[]> getguanliyuankouling()
    {
        try
        {
            connect();
            dos.writeUTF(Constant.getguanliyuankouling);
            message = din.readUTF();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static void deletehuodong(String mes) {
        try {
            connect();
            dos.writeUTF(Constant.deletehuodong + MyConverter.escape(mes));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
    }
    public static void deletexiangce(String content)
    {
        try{
            connect();
            dos.writeUTF(Constant.deletexiangce+MyConverter.escape(content));
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            disConnect();
        }
    }
    public static void insertxiangce(String content)
    {
        try{
            connect();
            dos.writeUTF(Constant.insertxiangce+MyConverter.escape(content));

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            disConnect();
        }
    }
    public static void updataxiangce(String content)
    {
        try{
            connect();
            dos.writeUTF(Constant.updataxiangce+MyConverter.escape(content));

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            disConnect();
        }
    }
    public static List<String[]> getxiangcebyid(String content){
        try{
            connect();
            dos.writeUTF(Constant.GetXiangceById+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static String getuserId2(String content){
        try{
            connect();
            dos.writeUTF(Constant.getid+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            disConnect();
        }
        return MyConverter.unescape(message);
    }
    public static List<String[]> getallguanlihuodongid(String content){
        try{
            connect();
            dos.writeUTF(Constant.getallguanlihuodongid+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static String getuserpen(String content)
    {
        try
        {
            connect();
            dos.writeUTF(Constant.GETUSERPEN+MyConverter.escape(content));
            message=din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally
        {
            disConnect();
        }
        return MyConverter.unescape(message);
    }
    public static List<String[]> getshetuantohuodong(String content){
        try{
            connect();
            dos.writeUTF(Constant.getshetuantohuodong+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> gethuodongnameandpicture(String content){
        try{
            connect();
            dos.writeUTF(Constant.GET_USERNAME_AND_PICTURE+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> gethuodongxiangce(String content){
        try{
            connect();
            dos.writeUTF(Constant.GET_HUODONG_XIANGCE+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static String[] getallchengyuan(){
        try{
            connect();
            dos.writeUTF(Constant.getallchengyuan);
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToArray(MyConverter.unescape(message));
    }
    public static void shanchuhaoyou(String mes) {
        try {
            connect();
            dos.writeUTF(Constant.shanchuhaoyou + MyConverter.escape(mes));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
    }
    public static String[] getalluserid(){
        try{
            connect();
            dos.writeUTF(Constant.GET_ALLUSERID);
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToArray(MyConverter.unescape(message));
    }
    public static String getuserpicture(String content){
        try{
            connect();
            dos.writeUTF(Constant.GET_USERPICTURE+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return message;
    }
    public static List<String[]> getusernamebuid(String content){
        try{
            connect();
            dos.writeUTF(Constant.GET_USERNAMEBUID+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static String getuserstatic(String content){
        try{
            connect();
            dos.writeUTF(Constant.GET_USERSTATIC+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return message;
    }
    public static String getshetuanidbymima(String content){
        try{
            connect();
            dos.writeUTF(Constant.GET_SHETUANIDBYMIMA+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return message;
    }
    public static List<String[]> getcunzaishetuanchengyuan(String content){
        try{
            connect();
            dos.writeUTF(Constant.GET_CUNZAISHETUANCHENGYUAN+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> getshachushetuanchengyuan(String content){
        try{
            connect();
            dos.writeUTF(Constant.GET_SHANCHUSHETUANCHENGYUAN+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static void tianjiacunzaichengyuan(String mes) {
        try {
            connect();
            dos.writeUTF(Constant.GET_TIANJIACUNZAICHENGYUAN + MyConverter.escape(mes));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
    }
    public static List<String[]> gethuodongrenyuan(String content){
        try{
            connect();
            dos.writeUTF(Constant.GET_HUODONGRENYUANXINGMING+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static List<String[]> gethuodongrenyuanid(String content){
        try{
            connect();
            dos.writeUTF(Constant.GET_HUODONGRENYUANID+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return StrListChange.StrToList(MyConverter.unescape(message));
    }
    public static String getuserzhuanye(String content){
        try{
            connect();
            dos.writeUTF(Constant.GET_USERZHUANYE+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return MyConverter.unescape(message);
    }
    public static String getuserxueyuan(String content){
        try{
            connect();
            dos.writeUTF(Constant.GET_XUEYUAN+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return MyConverter.unescape(message);
    }
    public static String getusersex(String content){
        try{
            connect();
            dos.writeUTF(Constant.GET_USERSEX+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return MyConverter.unescape(message);
    }
    public static String getusershetuan(String content){
        try{
            connect();
            dos.writeUTF(Constant.GET_SUERSHETUAN+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return MyConverter.unescape(message);
    }
    public static String getuserphoto(String content){
        try{
            connect();
            dos.writeUTF(Constant.GetUserOnePhoto+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return MyConverter.unescape(message);
    }
    public static String pdshetuanrenyuan(String content){
        try{
            connect();
            dos.writeUTF(Constant.PD_SHETUANRENYUAN+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return MyConverter.unescape(message);
    }
    public static void inserthuodongrenyuan(String content){
        try {
            connect();
            dos.writeUTF(Constant.INSERT_HUODONGRENYUAN + MyConverter.escape(content));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
    }
    public static String shifoubaoming(String content){
        try{
            connect();
            dos.writeUTF(Constant.SHIFOUBAOMING+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return MyConverter.unescape(message);
    }
    public static String gethuodongleixing(String content){
        try{
            connect();
            dos.writeUTF(Constant.GET_HUODONGLEIXING+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return MyConverter.unescape(message);
    }
    public static String getshetuanrenzt(String content){
        try{
            connect();
            dos.writeUTF(Constant.GET_SHETUANRENZT+MyConverter.escape(content));
            message = din.readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            disConnect();
        }
        return MyConverter.unescape(message);
    }
}