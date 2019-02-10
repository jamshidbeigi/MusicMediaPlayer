package com.example.mohamadreza.musicmediaplayer.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class Music {

    @Id(autoincrement = true)
    @Unique
    private Long mDbId;
    @Unique
    private long mId;
    private String mTitle;
    private String mAlbum;
    private String mArtist;
    private String srcData;
    private int durationmusic;
    private long mLyricId;
    @ToOne(joinProperty = "mLyricId")
    private Lyric mLyric;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1255683360)
    private transient MusicDao myDao;
    @Generated(hash = 1159061353)
    private transient Long mLyric__resolvedKey;

    public Music(long id, String title, String album, String artist, String srcData, int durationmusic) {
        mId = id;
        mTitle = title;
        mAlbum = album;
        mArtist = artist;
        this.srcData = srcData;
        this.durationmusic = durationmusic;
    }

    public Music() {
    }

    @Generated(hash = 1372443689)
    public Music(Long mDbId, long mId, String mTitle, String mAlbum, String mArtist, String srcData,
            int durationmusic, long mLyricId) {
        this.mDbId = mDbId;
        this.mId = mId;
        this.mTitle = mTitle;
        this.mAlbum = mAlbum;
        this.mArtist = mArtist;
        this.srcData = srcData;
        this.durationmusic = durationmusic;
        this.mLyricId = mLyricId;
    }

    public int getDurationmusic() {
        return durationmusic;
    }

    public void setDurationmusic(int durationmusic) {
        this.durationmusic = durationmusic;
    }

    public String getSrcData() {
        return srcData;
    }

    public void setSrcData(String srcData) {
        this.srcData = srcData;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getAlbum() {
        return mAlbum;
    }

    public void setAlbum(String album) {
        mAlbum = album;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    public long getMId() {
        return this.mId;
    }

    public void setMId(long mId) {
        this.mId = mId;
    }

    public String getMTitle() {
        return this.mTitle;
    }

    public void setMTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getMAlbum() {
        return this.mAlbum;
    }

    public void setMAlbum(String mAlbum) {
        this.mAlbum = mAlbum;
    }

    public String getMArtist() {
        return this.mArtist;
    }

    public void setMArtist(String mArtist) {
        this.mArtist = mArtist;
    }

    public long getMLyricId() {
        return this.mLyricId;
    }

    public void setMLyricId(long mLyricId) {
        this.mLyricId = mLyricId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1571350364)
    public Lyric getMLyric() {
        long __key = this.mLyricId;
        if (mLyric__resolvedKey == null || !mLyric__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            LyricDao targetDao = daoSession.getLyricDao();
            Lyric mLyricNew = targetDao.load(__key);
            synchronized (this) {
                mLyric = mLyricNew;
                mLyric__resolvedKey = __key;
            }
        }
        return mLyric;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1701970976)
    public void setMLyric(@NotNull Lyric mLyric) {
        if (mLyric == null) {
            throw new DaoException(
                    "To-one property 'mLyricId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.mLyric = mLyric;
            mLyricId = mLyric.getMId();
            mLyric__resolvedKey = mLyricId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    public Long getMDbId() {
        return this.mDbId;
    }

    public void setMDbId(Long mDbId) {
        this.mDbId = mDbId;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1218270154)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMusicDao() : null;
    }
}
