package com.smallchill.test.version;

public class Version {
	
	public static void main(String[] args) {
		System.out.println(CalVersion2("6.7.0.1"));
		System.out.println(CalVersion(101122049));
	}
	
	/// 还原版本号
    private static int CalVersion2(String version)
    {
        int rValue = 0;
        String[] verArray = version.split("\\.");
        rValue = (Integer.parseInt(verArray[0]) << 24) | (Integer.parseInt(verArray[1]) << 16) | (Integer.parseInt(verArray[2]) << 8) | Integer.parseInt(verArray[3]);
        return rValue;
    }
    
    /// 计算版本号
    private static String CalVersion(int version)
    {
        String returnValue = "";
        returnValue += (version >> 24) + ".";
        returnValue += (((version >> 16) << 24) >> 24) + ".";
        returnValue += (((version >> 8) << 24) >> 24) + ".";
        returnValue += ((version << 24) >> 24);
        return returnValue;
    }
}
