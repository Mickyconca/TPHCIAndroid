package ar.edu.itba.quickfitness.database;

import android.util.Log;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import ar.edu.itba.quickfitness.api.model.CategoryOrSport;
import ar.edu.itba.quickfitness.api.model.Creator;
import ar.edu.itba.quickfitness.domain.CategoryDomain;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String fromCategoryOrSportToString(CategoryDomain category){
        return category == null ? null : category.toConvert();
    }

    @TypeConverter
    public static CategoryDomain fromStringToCategoryOrSport(String value){
        StringTokenizer tokenizer = new StringTokenizer(value, "|");
        ArrayList<String> strings = new ArrayList<>();
        String aux;
        while (tokenizer.hasMoreTokens()) {
            aux = tokenizer.nextToken();
            strings.add(aux);
        }

        return value == null ? null : new CategoryDomain(Integer.parseInt(strings.get(0)), strings.get(1), strings.get(2));
    }

    @TypeConverter
    public static String fromCreatorToString(Creator creator){
        return creator == null ? null : creator.toConvert();
    }

    @TypeConverter
    public static Creator fromStringToCreator(String value) {
        StringTokenizer tokenizer = new StringTokenizer(value, "|");
        ArrayList<String> strings = new ArrayList<>();
        String aux;
        while (tokenizer.hasMoreTokens()) {
            aux = tokenizer.nextToken();
            strings.add(aux);
            Log.d("CONVERTER", aux);
        }

        return value == null ? null : new Creator(Integer.parseInt(strings.get(0)), strings.get(1),
                strings.get(2), strings.get(3), fromTimestamp(Long.parseLong(strings.get(4))), fromTimestamp(Long.parseLong(strings.get(5))));
    }


}

