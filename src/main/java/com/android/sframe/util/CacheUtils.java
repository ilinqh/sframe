package com.android.sframe.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by lin on 2017/9/7.
 * 功能：本应用数据清理管理器
 */

public class CacheUtils {

    private static boolean hasCache = true;
    private static boolean hasSP = false;
    private static boolean hasDatabase = true;

    /**
     * 获取所有的缓存大小
     *
     * @param context
     * @return
     */
    public static String getAllCacheSize(Context context) {
        long all = 0;
        try {
            if (hasCache) {
                all += getFolderSize(context.getCacheDir());
            }
            if (hasSP) {
                all += getFolderSize(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));
            }
            if (hasDatabase) {
                all += getFolderSize(new File("/data/data/" + context.getPackageName() + "/databases"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StringUtils.formatFileSize(all);
    }

    /**
     * 清除所有的缓存
     *
     * @param context
     * @return
     */
    public static boolean cleanAllCache(Context context) {
        try {
            if (hasCache) {
                cleanInternalCache(context);
            }
            if (hasSP) {
                cleanSharedPreference(context);
            }

            if (hasDatabase) {
                cleanDatabases(context);
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 获取转换后的 CacheSize 缓存大小
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static String getCacheSize(Context context) throws Exception {
        return StringUtils.formatFileSize(getFolderSize(context.getCacheDir()));
    }

    /**
     * 获取转换后的文件缓存大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static String getCacheSize(File file) throws Exception {
        return StringUtils.formatFileSize(getFolderSize(file));
    }

    /**
     * 获取转换后的 Database 缓存大小
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static String getCacheDatabaseSize(Context context) throws Exception {
        return StringUtils.formatFileSize(getFolderSize(new File("/data/data/" + context.getPackageName() +
                "/databases")));
    }

    /**
     * 获取转换后的 SP 缓存大小
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static String getCacheSharePreferenceSize(Context context) throws Exception {
        return StringUtils.formatFileSize(getFolderSize(new File("/data/data/" + context.getPackageName() +
                "/shared_prefs")));
    }

    /**
     * 清除本应用内部缓存(/data/data/package_name/cache)
     *
     * @param context
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * 清除本应用所有数据库(/data/data/package_name/databases)
     *
     * @param context
     */
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "databases"));
    }

    /**
     * 清除本应用 SharedPreferences(/data/data/package_name/shared_prefs)
     *
     * @param context
     */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "shared_prefs"));
    }

    /**
     * 按名字清除应用数据库
     *
     * @param context
     * @param dbName
     */
    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * 清除 /data/data/package_name/files 下的内容
     *
     * @param context
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * 清除外部 Cache 下的内容(/mnt/sdcard/android/data/package_name/cache)
     *
     * @param context
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     * 清除自定义路径下的文件，只支持目录下的文件删除
     * <p>
     * ----- 慎用 -----
     *
     * @param filePath
     */
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    public static void cleanApplicationData(Context context, String... filePath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        for (String file :
                filePath) {
            cleanCustomCache(file);
        }
    }

    /**
     * 获取文件大小
     * Context.getExternalFilesDir() --> SDCard/Android/data/应用的包名/files/ 目录，一般放一些长时间保存的数据
     * Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();

            if (fileList == null) {
                return 0;
            }
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return size;
    }

    /**
     * 删除某个文件夹下面的文件，若传入的 directory 是个文件，则不做处理
     *
     * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

    /**
     * 删除指定目录下文件及目录
     *
     * @param filePath
     * @param deleteThisPath
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    // 如果下面还有文件
                    File[] files = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {
                        // 如果是文件，删除
                        file.delete();
                    } else {
                        // 如果是目录
                        if (file.listFiles().length == 0) {
                            // 目录下没有文件或目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setHasCache(boolean hasCache) {
        this.hasCache = hasCache;
    }

    public void setHasSP(boolean hasSP) {
        this.hasSP = hasSP;
    }

    public void setHasDatabase(boolean hasDatabase) {
        this.hasDatabase = hasDatabase;
    }
}