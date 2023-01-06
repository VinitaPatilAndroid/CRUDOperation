package com.demo.roomdemo.db;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfUserEntity;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfUserEntity;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfUserEntity;

  public UserDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserEntity = new EntityInsertionAdapter<UserEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `userinfo`(`id`,`name`,`email`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UserEntity value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getEmail() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getEmail());
        }
      }
    };
    this.__deletionAdapterOfUserEntity = new EntityDeletionOrUpdateAdapter<UserEntity>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `userinfo` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UserEntity value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfUserEntity = new EntityDeletionOrUpdateAdapter<UserEntity>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `userinfo` SET `id` = ?,`name` = ?,`email` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UserEntity value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getEmail() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getEmail());
        }
        stmt.bindLong(4, value.getId());
      }
    };
  }

  @Override
  public void insertUser(UserEntity user) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfUserEntity.insert(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteUser(UserEntity user) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfUserEntity.handle(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateUser(UserEntity user) {
    __db.beginTransaction();
    try {
      __updateAdapterOfUserEntity.handle(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<UserEntity> getAllUserInfo() {
    final String _sql = "SELECT * FROM userinfo ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final int _cursorIndexOfEmail = _cursor.getColumnIndexOrThrow("email");
      final List<UserEntity> _result = new ArrayList<UserEntity>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final UserEntity _item;
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        final String _tmpEmail;
        _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        _item = new UserEntity(_tmpId,_tmpName,_tmpEmail);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
