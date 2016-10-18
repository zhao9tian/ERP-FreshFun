package com.quxin.freshfun.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * Created by qucheng on 2016/10/11.
 */
public class VPNTool {
    private synchronized static String executeCmd(String cmd) throws IOException
    {
        Process process = Runtime.getRuntime().exec("cmd /c " + cmd);
        StringBuilder sbCmd = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()  ,"GB2312"));
        String line = "";
        while ((line = br.readLine()) != null)
        {
            sbCmd.append(line);
        }
        return sbCmd.toString();
    }

    public synchronized static boolean disconnectVPN(String vpnName) throws IOException
    {
        String cmd = "rasdial " + vpnName + " /disconnect";
        String result = executeCmd(cmd);

        if (result == null || result.contains("没有连接"))
            return false;
        else return true;
    }
    public synchronized static boolean connectVPN(String vpnName, String username, String password) throws IOException
    {
        String cmd = "rasdial " + vpnName + " " + username + " " + password;
        String result = executeCmd(cmd);
        if (result == null || !result.contains("已连接"))
            return false;
        return true;
    }


    public static void main(String[] args) throws IOException {
        System.out.println(connectVPN("http://jsq.re","qucheng123","qucheng"));;
//        disconnectVPN("test");
    }
}
