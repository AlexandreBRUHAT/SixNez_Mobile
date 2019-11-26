package com.sixnez.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "user")
class User(
    @PrimaryKey
    @ColumnInfo(name = "id")
    private var _id: Long,

    @ColumnInfo(name = "login")
    private var _login: String? = "",

    @ColumnInfo(name = "password")
    private var _password: String? = ""): BaseObservable(), Parcelable {

    var id: Long
        @Bindable get() = _id
        set(value) {
            _id = value
            notifyPropertyChanged(BR.id)
        }

    var login: String?
        @Bindable get() = _login
        set(value) {
            _login = value
            notifyPropertyChanged(BR.login)
        }

    var password: String?
        @Bindable get() = _password
        set(value) {
            _password = value
            notifyPropertyChanged(BR.password)
        }

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(_id)
        parcel.writeString(_login)
        parcel.writeString(_password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        var message = "Login : "+this.login+"\n"+
                "Mot de passe : "+this.password+"\n"
        return message
    }
}