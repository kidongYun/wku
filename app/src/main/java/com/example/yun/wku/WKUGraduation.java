package com.example.yun.wku;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

public class WKUGraduation {
    public static final int UNKOWN_TYPE = -1;

    // 원불교학과
    public final int WON_BUDDHISM_2012 = 0; public final int WON_BUDDHISM_2013 = 1; public final int WON_BUDDHISM_2014 = 2; public final int WON_BUDDHISM_2015 = 3; public final int WON_BUDDHISM_2016 = 4; public final int WON_BUDDHISM_2017 = 5; public final int WON_BUDDHISM_2018 = 6;

    // 국어국문학과
    public final int KOREAN_LITERATURE_2012 = 7; public final int KOREAN_LITERATURE_2013 = 8; public final int KOREAN_LITERATURE_2014 = 9; public final int KOREAN_LITERATURE_2015 = 10; public final int KOREAN_LITERATURE_2016 = 11; public final int KOREAN_LITERATURE_2017 = 12; public final int KOREAN_LITERATURE_2018 = 13;

    // 문예창작학과
    public final int CREATIVE_WRITING_2012 = 14; public final int CREATIVE_WRITING_2013 = 15; public final int CREATIVE_WRITING_2014 = 16; public final int CREATIVE_WRITING_2015 = 17; public final int CREATIVE_WRITING_2016 = 18; public final int CREATIVE_WRITING_2017 = 19; public  final int CREATIVE_WRITING_2018 = 20;

    // 영어영문학과
    public final int ENGLISH_LITERATURE_2012 = 21; public final int ENGLISH_LITERATURE_2013 = 22; public final int ENGLISH_LITERATURE_2014 = 23; public final int ENGLISH_LITERATURE_2015 = 24; public final int ENGLISH_LITERATURE_2016 = 25; public final int ENGLISH_LITERATURE_2017 = 26; public final int ENGLISH_LITERATURE_2018 = 27;

    // 중국학과
    public final int CHINA_2012 = 28; public final int CHINA_2013 = 29; public final int CHINA_2014 = 30; public final int CHINA_2015 = 31; public final int CHINA_2016 = 32; public final int CHINA_2017 = 33; public final int CHINA_2018 = 34;

    // 역사문화학부
    public final int HISTORY_CULTURE_2012 = 35; public final int HISTORY_CULTURE_2013 = 36; public final int HISTORY_CULTURE_2014 = 37; public final int HISTORY_CULTURE_2015 = 38; public final int HISTORY_CULTURE_2016 = 39; public final int HISTORY_CULTURE_2017 = 40; public final int HISTORY_CULTURE_2018 = 41;

    // 철학과
    public final int PHILOSOPHY_2012 = 42; public final int PHILOSOPHY_2013 = 43; public final int PHILOSOPHY_2014 = 44; public final int PHILOSOPHY_2015 = 45; public final int PHILOSOPHY_2016 = 46; public final int PHILOSOPHY_2017 = 47; public final int PHILOSOPHY_2018 = 48;

    // 음악과
    public final int MUSIC_2012 = 49; public final int MUSIC_2013 = 50; public final int MUSIC_2014 = 51; public final int MUSIC_2015 = 52; public final int MUSIC_2016 = 53; public final int MUSIC_2017 = 54; public final int MUSIC_2018 = 55;

    // 경영학부
    public final int BUSINESS_2012 = 56; public final int BUSINESS_2013 = 57; public final int BUSINESS_2014 = 58; public final int BUSINESS_2015 = 59; public final int BUSINESS_2016 = 60; public final int BUSINESS_2017 = 61; public final int BUSINESS_2018 = 62;

    // 경제학부
    public final int ECONOMY_2012 = 63; public final int ECONOMY_2013 = 64; public final int ECONOMY_2014 = 65; public final int ECONOMY_2015 = 66; public final int ECONOMY_2016 = 67; public final int ECONOMY_2017 = 68; public final int ECONOMY_2018 = 69;

    // 국제통상학부
    public final int INTERNATIONAL_TRADE_2012 = 70; public final int INTERNATIONAL_TRADE_2013 = 71; public final int INTERNATIONAL_TRADE_2014 = 72; public final int INTERNATIONAL_TRADE_2015 = 73; public final int INTERNATIONAL_TRADE_2016 = 74; public final int INTERNATIONAL_TRADE_2017 = 75; public final int INTERNATIONAL_TRADE_2018 = 76;

    // 원예산업학과
    public final int HORTICULTURE_2012 = 77; public final int HORTICULTURE_2013 = 78; public final int HORTICULTURE_2014 = 79; public final int HORTICULTURE_2015 = 80; public final int HORTICULTURE_2016 = 81; public final int HORTICULTURE_2017 = 82; public final int HORTICULTURE_2018 = 83;

    // 산림조경학과
    public final int FOREST_LANDSCAPE_2012 = 84; public final int FOREST_LANDSCAPE_2013 = 85; public final int FOREST_LANDSCAPE_2014 = 86; public final int FOREST_LANDSCAPE_2015 = 87; public final int FOREST_LANDSCAPE_2016 = 88; public final int FOREST_LANDSCAPE_2017 = 89; public final int FOREST_LANDSCAPE_2018 = 90;

    // 식품생명공학과
    public final int FOOD_LIFE_ENGINEERING_2012 = 91; public final int FOOD_LIFE_ENGINEERING_2013 = 92; public final int FOOD_LIFE_ENGINEERING_2014 = 93; public final int FOOD_LIFE_ENGINEERING_2015 = 94; public final int FOOD_LIFE_ENGINEERING_2016 = 95; public final int FOOD_LIFE_ENGINEERING_2017 = 96; public final int FOOD_LIFE_ENGINEERING_2018 = 97;

    // 생물환경화학과
    public final int BIOCHEMISTRY_2012 = 98; public final int BIOCHEMISTRY_2013 = 99; public final int BIOCHEMISTRY_2014 = 100; public final int BIOCHEMISTRY_2015 = 101; public final int BIOCHEMISTRY_2016 = 102; public final int BIOCHEMISTRY_2017 = 103; public final int BIOCHEMISTRY_2018 = 104;

    // 식품영양학과
    public final int FOOD_NUTRITION_2012 = 105; public final int FOOD_NUTRITION_2013 = 106; public final int FOOD_NUTRITION_2014 = 107; public final int FOOD_NUTRITION_2015 = 108; public final int FOOD_NUTRITION_2016 = 109; public final int FOOD_NUTRITION_2017 = 110; public final int FOOD_NUTRITION_2018 = 111;

    // 한약학과
    public final int ORIENTAL_MEDICINE_2012 = 112; public final int ORIENTAL_MEDICINE_2013 = 113; public final int ORIENTAL_MEDICINE_2014 = 114; public final int ORIENTAL_MEDICINE_2015 = 115; public final int ORIENTAL_MEDICINE_2016 = 116; public final int ORIENTAL_MEDICINE_2017 = 117; public final int ORIENTAL_MEDICINE_2018 = 118;

    // 국어교육과
    public final int KOREAN_LANGUAGE_EDUCATION_2012 = 119; public final int KOREAN_LANGUAGE_EDUCATION_2013 = 120; public final int KOREAN_LANGUAGE_EDUCATION_2014 = 121; public final int KOREAN_LANGUAGE_EDUCATION_2015 = 122; public final int KOREAN_LANGUAGE_EDUCATION_2016 = 123; public final int KOREAN_LANGUAGE_EDUCATION_2017 = 124; public final int KOREAN_LANGUAGE_EDUCATION_2018 = 125;

    // 영어교육과
    public final int ENGLISH_LANGUAGE_EDUCATION_2012 = 126; public final int ENGLISH_LANGUAGE_EDUCATION_2013 = 127; public final int ENGLISH_LANGUAGE_EDUCATION_2014 = 128; public final int ENGLISH_LANGUAGE_EDUCATION_2015 = 129; public final int ENGLISH_LANGUAGE_EDUCATION_2016 = 130; public final int ENGLISH_LANGUAGE_EDUCATION_2017 = 131; public final int ENGLISH_LANGUAGE_EDUCATION_2018 = 132;

    // 일어교육과
    public final int JAPANESE_LANGUAGE_EDUCATION_2012 = 133; public final int JAPANESE_LANGUAGE_EDUCATION_2013 = 134; public final int JAPANESE_LANGUAGE_EDUCATION_2014 = 135; public final int JAPANESE_LANGUAGE_EDUCATION_2015 = 136; public final int JAPANESE_LANGUAGE_EDUCATION_2016 = 137; public final int JAPANESE_LANGUAGE_EDUCATION_2017 = 138; public final int JAPANESE_LANGUAGE_EDUCATION_2018 = 139;

    // 한문교육과
    public final int CHINESE_LANGUAGE_EDUCATION_2012 = 140; public final int CHINESE_LANGUAGE_EDUCATION_2013 = 141; public final int CHINESE_LANGUAGE_EDUCATION_2014 = 142; public final int CHINESE_LANGUAGE_EDUCATION_2015 = 143; public final int CHINESE_LANGUAGE_EDUCATION_2016 = 144; public final int CHINESE_LANGUAGE_EDUCATION_2017 = 145; public final int CHINESE_LANGUAGE_EDUCATION_2018 = 146;

    // 역사교육과
    public final int HISTORY_LANGUAGE_EDUCATION_2012 = 147; public final int HISTORY_LANGUAGE_EDUCATION_2013 = 148; public final int HISTORY_LANGUAGE_EDUCATION_2014 = 149; public final int HISTORY_LANGUAGE_EDUCATION_2015 = 150; public final int HISTORY_LANGUAGE_EDUCATION_2016 = 151; public final int HISTORY_LANGUAGE_EDUCATION_2017 = 152; public final int HISTORY_LANGUAGE_EDUCATION_2018 = 153;

    // 교육학과
    public final int EDUCATION_2012 = 154; public final int EDUCATION_2013 = 155; public final int EDUCATION_2014 = 156; public final int EDUCATION_2015 = 157; public final int EDUCATION_2016 = 158; public final int EDUCATION_2017 = 159; public final int EDUCATION_2018 = 160;

    // 유아교육과
    public final int CHILDHOOD_EDUCATION_2012 = 161; public final int CHILDHOOD_EDUCATION_2013 = 162; public final int CHILDHOOD_EDUCATION_2014 = 163; public final int CHILDHOOD_EDUCATION_2015 = 164; public final int CHILDHOOD_EDUCATION_2016 = 165; public final int CHILDHOOD_EDUCATION_2017 = 166; public final int CHILDHOOD_EDUCATION_2018 = 167;

    // 가정교육과
    public final int HOME_EDUCATION_2012 = 168; public final int HOME_EDUCATION_2013 = 169; public final int HOME_EDUCATION_2014 = 170; public final int HOME_EDUCATION_2015 = 171; public final int HOME_EDUCATION_2016 = 172; public final int HOME_EDUCATION_2017 = 173; public final int HOME_EDUCATION_2018 = 174;

    // 체육교육과
    public final int PHYSICAL_EDUCATION_2012 = 175; public final int PHYSICAL_EDUCATION_2013 = 176; public final int PHYSICAL_EDUCATION_2014 = 177; public final int PHYSICAL_EDUCATION_2015 = 178; public final int PHYSICAL_EDUCATION_2016 = 179; public final int PHYSICAL_EDUCATION_2017 = 180; public final int PHYSICAL_EDUCATION_2018 = 181;

    // 중등특수교육과
    public final int SECONDARY_SPECIAL_EDUCATION_2012 = 182; public final int SECONDARY_SPECIAL_EDUCATION_2013 = 183; public final int SECONDARY_SPECIAL_EDUCATION_2014 = 184; public final int SECONDARY_SPECIAL_EDUCATION_2015 = 185; public final int SECONDARY_SPECIAL_EDUCATION_2016 = 186; public final int SECONDARY_SPECIAL_EDUCATION_2017 = 187; public final int SECONDARY_SPECIAL_EDUCATION_2018 = 188;

    // 수학교육과
    public final int MATHEMATICAL_EDUCATION_2012 = 189; public final int MATHEMATICAL_EDUCATION_2013 = 190; public final int MATHEMATICAL_EDUCATION_2014 = 191; public final int MATHEMATICAL_EDUCATION_2015 = 192; public final int MATHEMATICAL_EDUCATION_2016 = 193; public final int MATHEMATICAL_EDUCATION_2017 = 194; public final int MATHEMATICAL_EDUCATION_2018 = 195;

    // 수학정보통계학부
    public final int MATHEMATICAL_INFORMATION_STATISTICS_2012 = 196; public final int MATHEMATICAL_INFORMATION_STATISTICS_2013 = 197; public final int MATHEMATICAL_INFORMATION_STATISTICS_2014 = 198; public final int MATHEMATICAL_INFORMATION_STATISTICS_2015 = 199; public final int MATHEMATICAL_INFORMATION_STATISTICS_2016 = 200; public final int MATHEMATICAL_INFORMATION_STATISTICS_2017 = 201; public final int MATHEMATICAL_INFORMATION_STATISTICS_2018 = 202;

    // 응용수학부
    public final int APPLIED_MATHEMATICS_2018 = 483;

    // 빅데이터 금융통계학부
    public final int BIGDATA_FININACIAL_STATISTICAL_2018 = 484;

    // 바이오나노화학부
    public final int BIO_NANO_CHEMICAL_2012 = 203; public final int BIO_NANO_CHEMICAL_2013 = 204; public final int BIO_NANO_CHEMICAL_2014 = 205; public final int BIO_NANO_CHEMICAL_2015 = 206; public final int BIO_NANO_CHEMICAL_2016 = 207; public final int BIO_NANO_CHEMICAL_2017 = 208; public final int BIO_NANO_CHEMICAL_2018 = 209;

    // 반도체디스플레이학부
    public final int SEMICONDUCTOR_DISPLAY_2012 = 210; public final int SEMICONDUCTOR_DISPLAY_2013 = 211; public final int SEMICONDUCTOR_DISPLAY_2014 = 212; public final int SEMICONDUCTOR_DISPLAY_2015 = 213; public final int SEMICONDUCTOR_DISPLAY_2016 = 214; public final int SEMICONDUCTOR_DISPLAY_2017 = 215; public final int SEMICONDUCTOR_DISPLAY_2018 = 216;

    // 생명과학부
    public final int LIFE_SCIENCE_2012 = 217; public final int LIFE_SCIENCE_2013 = 218; public final int LIFE_SCIENCE_2014 = 219; public final int LIFE_SCIENCE_2015 = 220; public final int LIFE_SCIENCE_2016 = 221; public final int LIFE_SCIENCE_2017 = 222; public final int LIFE_SCIENCE_2018 = 223;

    // 스포츠과학부
    public final int SPORTS_SCIENCE_2012 = 224; public final int SPORTS_SCIENCE_2013 = 225; public final int SPORTS_SCIENCE_2014 = 226; public final int SPORTS_SCIENCE_2015 = 227; public final int SPORTS_SCIENCE_2016 = 228; public final int SPORTS_SCIENCE_2017 = 229; public final int SPORTS_SCIENCE_2018 = 230;

    // 뷰티디자인학부
    public final int BEAUTY_DESIGN_2012 = 231; public final int BEAUTY_DESIGN_2013 = 232; public final int BEAUTY_DESIGN_2014 = 233; public final int BEAUTY_DESIGN_2015 = 234; public final int BEAUTY_DESIGN_2016 = 235; public final int BEAUTY_DESIGN_2017 = 236; public final int BEAUTY_DESIGN_2018 = 237;

    // 한의예과
    public final int ORIENTAL_TREATMENT_MEDICINE_2012 = 238; public final int ORIENTAL_TREATMENT_MEDICINE_2013 = 239; public final int ORIENTAL_TREATMENT_MEDICINE_2014 = 240; public final int ORIENTAL_TREATMENT_MEDICINE_2015 = 241; public final int ORIENTAL_TREATMENT_MEDICINE_2016 = 242; public final int ORIENTAL_TREATMENT_MEDICINE_2017 = 243; public final int ORIENTAL_TREATMENT_MEDICINE_2018 = 244;

    // 한의학과
    public final int ORIENTAL_SCHOLARSHIP_MEDICINE_2012 = 245; public final int ORIENTAL_SCHOLARSHIP_MEDICINE_2013 = 246; public final int ORIENTAL_SCHOLARSHIP_MEDICINE_2014 = 247; public final int ORIENTAL_SCHOLARSHIP_MEDICINE_2015 = 248; public final int ORIENTAL_SCHOLARSHIP_MEDICINE_2016 = 249; public final int ORIENTAL_SCHOLARSHIP_MEDICINE_2017 = 250; public final int ORIENTAL_SCHOLARSHIP_MEDICINE_2018 = 251;

    // 미술과
    public final int ART_2012 = 252; public final int ART_2013 = 253; public final int ART_2014 = 254; public final int ART_2015 = 255; public final int ART_2016 = 256; public final int ART_2017 = 257; public final int ART_2018 = 258;

    // 귀금속보석공예과
    public final int METALLIC_JEWELRY_2012 = 259; public final int METALLIC_JEWELRY_2013 = 260; public final int METALLIC_JEWELRY_2014 = 261; public final int METALLIC_JEWELRY_2015 = 262; public final int METALLIC_JEWELRY_2016 = 263; public final int METALLIC_JEWELRY_2017 = 264; public final int METALLIC_JEWELRY_2018 = 265;

    // 디자인학부
    public final int DESIGN_2012 = 266; public final int DESIGN_2013 = 267; public final int DESIGN_2014 = 268; public final int DESIGN_2015 = 269; public final int DESIGN_2016 = 270; public final int DESIGN_2017 = 271; public final int DESIGN_2018 = 272;

    // 패션디자인산업학과
    public final int FASHION_DESIGN_2012 = 273; public final int FASHION_DESIGN_2013 = 274; public final int FASHION_DESIGN_2014 = 275; public final int FASHION_DESIGN_2015 = 276; public final int FASHION_DESIGN_2016 = 277; public final int FASHION_DESIGN_2017 = 278; public final int FASHION_DESIGN_2018 = 279;

    // 행정언론학부
    public final int PUBLIC_ADMINISTRATION_2012 = 280; public final int PUBLIC_ADMINISTRATION_2013 = 281; public final int PUBLIC_ADMINISTRATION_2014 = 282; public final int PUBLIC_ADMINISTRATION_2015 = 283; public final int PUBLIC_ADMINISTRATION_2016 = 284; public final int PUBLIC_ADMINISTRATION_2017 = 285; public final int PUBLIC_ADMINISTRATION_2018 = 286;

    // 복지보건학부(사회복지학)
    public final int SOCIAL_WELFARE_2012 = 287; public final int SOCIAL_WELFARE_2013 = 288; public final int SOCIAL_WELFARE_2014 = 289; public final int SOCIAL_WELFARE_2015 = 290; public final int SOCIAL_WELFARE_2016 = 291; public final int SOCIAL_WELFARE_2017 = 292; public final int SOCIAL_WELFARE_2018 = 293;

    // 복지보건학부(보건행정학)
    public final int HEALTH_ADMINISTRATION_2012 = 294; public final int HEALTH_ADMINISTRATION_2013 = 295; public final int HEALTH_ADMINISTRATION_2014 = 296; public final int HEALTH_ADMINISTRATION_2015 = 297; public final int HEALTH_ADMINISTRATION_2016 = 298; public final int HEALTH_ADMINISTRATION_2017 = 299; public final int HEALTH_ADMINISTRATION_2018 = 300;

    // 가정아동복지학과
    public final int CHILD_WELFARE_2012 = 301; public final int CHILD_WELFARE_2013 = 302; public final int CHILD_WELFARE_2014 = 303; public final int CHILD_WELFARE_2015 = 304; public final int CHILD_WELFARE_2016 = 305; public final int CHILD_WELFARE_2017 = 306; public final int CHILD_WELFARE_2018 = 307;


    // 군사학과
    public final int MILITARY_2012 = 308; public final int MILITARY_2013 = 309; public final int MILITARY_2014 = 310; public final int MILITARY_2015 = 311; public final int MILITARY_2016 = 312; public final int MILITARY_2017 = 313; public final int MILITARY_2018 = 314;

    // 경찰행정학과
    public final int POLICE_ADMINISTRATION_2012 = 315; public final int POLICE_ADMINISTRATION_2013 = 316; public final int POLICE_ADMINISTRATION_2014 = 317; public final int POLICE_ADMINISTRATION_2015 = 318; public final int POLICE_ADMINISTRATION_2016 = 319; public final int POLICE_ADMINISTRATION_2017 = 320; public final int POLICE_ADMINISTRATION_2018 = 321;

    // 소방행정학과
    public final int FIRE_ADMINISTRATION_2012 = 322; public final int FIRE_ADMINISTRATION_2013 = 323; public final int FIRE_ADMINISTRATION_2014 = 324; public final int FIRE_ADMINISTRATION_2015 = 325; public final int FIRE_ADMINISTRATION_2016 = 326; public final int FIRE_ADMINISTRATION_2017 = 327; public final int FIRE_ADMINISTRATION_2018 = 328;

    // 전기공학과
    public final int ELECTRICAL_ENGINEERING_2012 = 329; public final int ELECTRICAL_ENGINEERING_2013 = 330; public final int ELECTRICAL_ENGINEERING_2014 = 331; public final int ELECTRICAL_ENGINEERING_2015 = 332; public final int ELECTRICAL_ENGINEERING_2016 = 333; public final int ELECTRICAL_ENGINEERING_2017 = 334; public final int ELECTRICAL_ENGINEERING_2018 = 335;

    // 정보통신공학과
    public final int INFORMATION_COMMUNICATION_ENGINEERING_2012 = 336; public final int INFORMATION_COMMUNICATION_ENGINEERING_2013 = 337; public final int INFORMATION_COMMUNICATION_ENGINEERING_2014 = 338; public final int INFORMATION_COMMUNICATION_ENGINEERING_2015 = 339; public final int INFORMATION_COMMUNICATION_ENGINEERING_2016 = 340; public final int INFORMATION_COMMUNICATION_ENGINEERING_2017 = 341; public final int INFORMATION_COMMUNICATION_ENGINEERING_2018 = 342;

    // 전자공학과
    public final int ELECTRONICAL_ENGINEERING_2012 = 343; public final int ELECTRONICAL_ENGINEERING_2013 = 344; public final int ELECTRONICAL_ENGINEERING_2014 = 345; public final int ELECTRONICAL_ENGINEERING_2015 = 346; public final int ELECTRONICAL_ENGINEERING_2016 = 347; public final int ELECTRONICAL_ENGINEERING_2017 = 348; public final int ELECTRONICAL_ENGINEERING_2018 = 349;

    // 전자융합공학과
    public final int ELECTRONIC_CONVERGENCE_ENGINEERING_2012 = 350; public final int ELECTRONIC_CONVERGENCE_ENGINEERING_2013 = 351; public final int ELECTRONIC_CONVERGENCE_ENGINEERING_2014 = 352; public final int ELECTRONIC_CONVERGENCE_ENGINEERING_2015 = 353; public final int ELECTRONIC_CONVERGENCE_ENGINEERING_2016 = 354; public final int ELECTRONIC_CONVERGENCE_ENGINEERING_2017 = 355; public final int ELECTRONIC_CONVERGENCE_ENGINEERING_2018 = 356;

    // 컴퓨터소프트웨어공학과
    public final int COMPUTER_ENGINEERING_2012 = 357; public final int COMPUTER_ENGINEERING_2013 = 358; public final int COMPUTER_ENGINEERING_2014 = 359; public final int COMPUTER_ENGINEERING_2015 = 360; public final int COMPUTER_ENGINEERING_2016 = 361; public final int COMPUTER_SOFTWARE_ENGINEERING_2017 = 362; public final int COMPUTER_SOFTWARE_ENGINEERING_2018 = 363;

    // 디지털콘텐츠공학과
    public final int DIGITAL_CONTENT_ENGINEERING_2012 = 364; public final int DIGITAL_CONTENT_ENGINEERING_2013 = 365; public final int DIGITAL_CONTENT_ENGINEERING_2014 = 366; public final int DIGITAL_CONTENT_ENGINEERING_2015 = 367; public final int DIGITAL_CONTENT_ENGINEERING_2016 = 368; public final int DIGITAL_CONTENT_ENGINEERING_2017 = 369; public final int DIGITAL_CONTENT_ENGINEERING_2018 = 370;

    // 기계공학과
    public final int MECHANICAL_ENGINEERING_2012 = 371; public final int MECHANICAL_ENGINEERING_2013 = 372; public final int MECHANICAL_ENGINEERING_2014 = 373; public final int MECHANICAL_ENGINEERING_2015 = 374; public final int MECHANICAL_ENGINEERING_2016 = 375; public final int MECHANICAL_ENGINEERING_2017 = 376; public final int MECHANICAL_ENGINEERING_2018 = 377;

    // 스마트자동차공학과
    public final int SMART_CAR_ENGINEERING_2012 = 378; public final int SMART_CAR_ENGINEERING_2013 = 379; public final int SMART_CAR_ENGINEERING_2014 = 380; public final int SMART_CAR_ENGINEERING_2015 = 381; public final int SMART_CAR_ENGINEERING_2016 = 382; public final int SMART_CAR_ENGINEERING_2017 = 383; public final int SMART_CAR_ENGINEERING_2018 = 384;

    // 기계설계공학과
    public final int MECHANICAL_DESIGN_ENGINEERING_2012 = 385; public final int MECHANICAL_DESIGN_ENGINEERING_2013 = 386; public final int MECHANICAL_DESIGN_ENGINEERING_2014 = 387; public final int MECHANICAL_DESIGN_ENGINEERING_2015 = 388; public final int MECHANICAL_DESIGN_ENGINEERING_2016 = 389; public final int MECHANICAL_DESIGN_ENGINEERING_2017 = 390; public final int MECHANICAL_DESIGN_ENGINEERING_2018 = 391;

    // 건축공학과
    public final int ARCHITECTURAL_ENGINEERING_2012 = 392; public final int ARCHITECTURAL_ENGINEERING_2013 = 393; public final int ARCHITECTURAL_ENGINEERING_2014 = 394; public final int ARCHITECTURAL_ENGINEERING_2015 = 395; public final int ARCHITECTURAL_ENGINEERING_2016 = 396; public final int ARCHITECTURAL_ENGINEERING_2017 = 397; public final int ARCHITECTURAL_ENGINEERING_2018 = 398;

    // 도시공학부
    public final int URBAN_ENGINEERING_2012 = 399; public final int URBAN_ENGINEERING_2013 = 400; public final int URBAN_ENGINEERING_2014 = 401; public final int URBAN_ENGINEERING_2015 = 402; public final int URBAN_ENGINEERING_2016 = 403; public final int URBAN_ENGINEERING_2017 = 404; public final int URBAN_ENGINEERING_2018 = 405;

    // 화학융합공학과
    public final int CHEMISTRY_CONVERGENCE_ENGINEERING_2012 = 406; public final int CHEMISTRY_CONVERGENCE_ENGINEERING_2013 = 407; public final int CHEMISTRY_CONVERGENCE_ENGINEERING_2014 = 408; public final int CHEMISTRY_CONVERGENCE_ENGINEERING_2015 = 409; public final int CHEMISTRY_CONVERGENCE_ENGINEERING_2016 = 410; public final int CHEMISTRY_CONVERGENCE_ENGINEERING_2017 = 411; public final int CHEMISTRY_CONVERGENCE_ENGINEERING_2018 = 412;

    // 탄소융합공학과
    public final int CARBON_CONVERGENCE_ENGINEERING_2012 = 413; public final int CARBON_CONVERGENCE_ENGINEERING_2013 = 414; public final int CARBON_CONVERGENCE_ENGINEERING_2014 = 415; public final int CARBON_CONVERGENCE_ENGINEERING_2015 = 416; public final int CARBON_CONVERGENCE_ENGINEERING_2016 = 417; public final int CARBON_CONVERGENCE_ENGINEERING_2017 = 418; public final int CARBON_CONVERGENCE_ENGINEERING_2018 = 419;

    // 건축학과
    public final int ARCHITECTURE_2012 = 420; public final int ARCHITECTURE_2013 = 421; public final int ARCHITECTURE_2014 = 422; public final int ARCHITECTURE_2015 = 423; public final int ARCHITECTURE_2016 = 424; public final int ARCHITECTURE_2017 = 425; public final int ARCHITECTURE_2018 = 426;

    // 토목환경공학과
    public final int CIVIL_ENGINEERING_2012 = 427; public final int CIVIL_ENGINEERING_2013 = 428; public final int CIVIL_ENGINEERING_2014 = 429; public final int CIVIL_ENGINEERING_2015 = 430; public final int CIVIL_ENGINEERING_2016 = 431; public final int CIVIL_ENGINEERING_2017 = 432; public final int CIVIL_ENGINEERING_2018 = 433;

    // 치의예과
    public final int DENTISTRY_TREATMENT_2012 = 434;  public final int DENTISTRY_TREATMENT_2013 = 435; public final int DENTISTRY_TREATMENT_2014 = 436; public final int DENTISTRY_TREATMENT_2015 = 437; public final int DENTISTRY_TREATMENT_2016 = 438; public final int DENTISTRY_TREATMENT_2017 = 439; public final int DENTISTRY_TREATMENT_2018 = 440;

    // 치의학과
    public final int DENTISTRY_SCHOLARSHIP_2012 = 441; public final int DENTISTRY_SCHOLARSHIP_2013 = 442; public final int DENTISTRY_SCHOLARSHIP_2014 = 443; public final int DENTISTRY_SCHOLARSHIP_2015 = 444; public final int DENTISTRY_SCHOLARSHIP_2016 = 445; public final int DENTISTRY_SCHOLARSHIP_2017 = 446; public final int DENTISTRY_SCHOLARSHIP_2018 = 447;

    // 의예과
    public final int MEDICAL_TREATMENT_2012 = 448; public final int MEDICAL_TREATMENT_2013 = 449; public final int MEDICAL_TREATMENT_2014 = 450; public final int MEDICAL_TREATMENT_2015 = 451; public final int MEDICAL_TREATMENT_2016 = 452; public final int MEDICAL_TREATMENT_2017 = 453; public final int MEDICAL_TREATMENT_2018 = 454;

    // 의학과
    public final int MEDICAL_SCHOLARSHIP_2012 = 455; public final int MEDICAL_SCHOLARSHIP_2013 = 456; public final int MEDICAL_SCHOLARSHIP_2014 = 457; public final int MEDICAL_SCHOLARSHIP_2015 = 458; public final int MEDICAL_SCHOLARSHIP_2016 = 459; public final int MEDICAL_SCHOLARSHIP_2017 = 460; public final int MEDICAL_SCHOLARSHIP_2018 = 461;

    // 간호학과
    public final int NURSING_SCIENCE_2012 = 462; public final int NURSING_SCIENCE_2013 = 463; public final int NURSING_SCIENCE_2014 = 464; public final int NURSING_SCIENCE_2015 = 465; public final int NURSING_SCIENCE_2016 = 466; public final int NURSING_SCIENCE_2017 = 467; public final int NURSING_SCIENCE_2018 = 468;

    // 작업치료학과
    public final int OCCUPATIONAL_THERAPY_2012 = 469; public final int OCCUPATIONAL_THERAPY_2013 = 470; public final int OCCUPATIONAL_THERAPY_2014 = 471; public final int OCCUPATIONAL_THERAPY_2015 = 472; public final int OCCUPATIONAL_THERAPY_2016 = 473; public final int OCCUPATIONAL_THERAPY_2017 = 474; public final int OCCUPATIONAL_THERAPY_2018 = 475;

    // 약학과
    public final int PHARMACY_2012 = 476; public final int PHARMACY_2013 = 477; public final int PHARMACY_2014 = 478; public final int PHARMACY_2015 = 479; public final int PHARMACY_2016 = 480; public final int PHARMACY_2017 = 481; public final int PHARMACY_2018 = 482;

    private int TYPE = UNKOWN_TYPE;

    private Context context;
    private String major;
    private int studentNo;

    private final int NONE = -100;

    private ArrayList<WKUGraduationData> wkuGraduationDatas;
    private WKUGraduationData wkuGraduationData;

    private WKUDatabase wkuDatabase;

    public WKUGraduation(Context context, String major, int studentNo) {
        this.context = context;
        this.major = major;
        this.studentNo = studentNo;

        wkuDatabase = new WKUDatabase(context);
        wkuGraduationDatas = new ArrayList<>();
        wkuGraduationData = new WKUGraduationData();
        wkuGraduationData.setGraduationType(UNKOWN_TYPE);

        createType();
        setCurCredit();
    }

    public void setCurCredit() {
        setCurGraduationCredit();
        setCurMajorCredit();
        setCurMajorBasisCredit();
        setCurGeneralCredit();
        setCurGeneralAffiliationCredit();
    }

    public void createTable() {

        // 원불교학과
        wkuGraduationDatas.add(new WKUGraduationData(WON_BUDDHISM_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(WON_BUDDHISM_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(WON_BUDDHISM_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(WON_BUDDHISM_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(WON_BUDDHISM_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(WON_BUDDHISM_2017, 130, 69, 54, 18, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(WON_BUDDHISM_2018, 130, 69, 36, 18, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));

        // 국어국문학과
        wkuGraduationDatas.add(new WKUGraduationData(KOREAN_LITERATURE_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(KOREAN_LITERATURE_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(KOREAN_LITERATURE_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(KOREAN_LITERATURE_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(KOREAN_LITERATURE_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(KOREAN_LITERATURE_2017, 130, 66, 36, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(KOREAN_LITERATURE_2018, 130, 66, 36, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));

        // 문예창작학과
        wkuGraduationDatas.add(new WKUGraduationData(CREATIVE_WRITING_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CREATIVE_WRITING_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CREATIVE_WRITING_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CREATIVE_WRITING_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CREATIVE_WRITING_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CREATIVE_WRITING_2017, 130, 66, 36, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(CREATIVE_WRITING_2018, 130, 66, 36, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));

        // 영어영문학과
        wkuGraduationDatas.add(new WKUGraduationData(ENGLISH_LITERATURE_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ENGLISH_LITERATURE_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ENGLISH_LITERATURE_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ENGLISH_LITERATURE_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ENGLISH_LITERATURE_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ENGLISH_LITERATURE_2017, 130, 66, 36, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(ENGLISH_LITERATURE_2018, 130, 66, 36, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));

        // 중국학과
        wkuGraduationDatas.add(new WKUGraduationData(CHINA_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CHINA_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CHINA_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CHINA_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CHINA_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CHINA_2017, 130, 66, 36, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(CHINA_2018, 130, 66, 36, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));

        // 역사문화학부
        wkuGraduationDatas.add(new WKUGraduationData(HISTORY_CULTURE_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(HISTORY_CULTURE_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(HISTORY_CULTURE_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(HISTORY_CULTURE_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(HISTORY_CULTURE_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(HISTORY_CULTURE_2017, 130, 66, 36, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(HISTORY_CULTURE_2018, 130, 66, 36, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));

        // 철학과
        wkuGraduationDatas.add(new WKUGraduationData(PHILOSOPHY_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(PHILOSOPHY_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(PHILOSOPHY_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(PHILOSOPHY_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(PHILOSOPHY_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(PHILOSOPHY_2017, 130, 66, 36, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(PHILOSOPHY_2018, 130, 66, 36, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));

        // 음악과
        wkuGraduationDatas.add(new WKUGraduationData(MUSIC_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MUSIC_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MUSIC_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MUSIC_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MUSIC_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MUSIC_2017, 130, 66, 48, 19, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(MUSIC_2018, 130, 66, 48, 19, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));

        // 경영학부
        wkuGraduationDatas.add(new WKUGraduationData(BUSINESS_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(BUSINESS_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(BUSINESS_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(BUSINESS_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(BUSINESS_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(BUSINESS_2017, 130, 66, 51, 30, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(BUSINESS_2018, 130, 66, 51, 30, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));

        // 경제학부
        wkuGraduationDatas.add(new WKUGraduationData(ECONOMY_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ECONOMY_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ECONOMY_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ECONOMY_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ECONOMY_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ECONOMY_2017, 130, 66, 36, 24, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(ECONOMY_2018, 130, 66, 36, 24, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));

        // 국제통상학부
        wkuGraduationDatas.add(new WKUGraduationData(INTERNATIONAL_TRADE_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(INTERNATIONAL_TRADE_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(INTERNATIONAL_TRADE_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(INTERNATIONAL_TRADE_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(INTERNATIONAL_TRADE_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(INTERNATIONAL_TRADE_2017, 130, 66, 36, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(INTERNATIONAL_TRADE_2018, 130, 66, 36, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));

        // 원예산업학과
        wkuGraduationDatas.add(new WKUGraduationData(HORTICULTURE_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(HORTICULTURE_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(HORTICULTURE_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(HORTICULTURE_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(HORTICULTURE_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(HORTICULTURE_2017, 130, 69, 48, 19, 29, 5, 6, 4, 4, NONE, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(HORTICULTURE_2018, 130, 69, 48, 19, 29, 5, 6, 4, 4, NONE, 6, 2, 2, 60));

        // 산림조경학과
        wkuGraduationDatas.add(new WKUGraduationData(FOREST_LANDSCAPE_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FOREST_LANDSCAPE_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FOREST_LANDSCAPE_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FOREST_LANDSCAPE_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FOREST_LANDSCAPE_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FOREST_LANDSCAPE_2017, 130, 69, 48, 19, 29, 5, 6, 4, 4, NONE, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(FOREST_LANDSCAPE_2018, 130, 69, 48, 19, 29, 5, 6, 4, 4, NONE, 6, 2, 2, 60));

        // 식품생명공학과
        wkuGraduationDatas.add(new WKUGraduationData(FOOD_LIFE_ENGINEERING_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FOOD_LIFE_ENGINEERING_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FOOD_LIFE_ENGINEERING_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FOOD_LIFE_ENGINEERING_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FOOD_LIFE_ENGINEERING_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FOOD_LIFE_ENGINEERING_2017, 130, 75, 48, 19, 37, 5, 12, 2, 4, 4, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(FOOD_LIFE_ENGINEERING_2018, 130, 69, 48, 19, 37, 5, 12, 2, 4, 4, 6, 2, 2, 60));

        // 생물환경화학과
        wkuGraduationDatas.add(new WKUGraduationData(BIOCHEMISTRY_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(BIOCHEMISTRY_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(BIOCHEMISTRY_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(BIOCHEMISTRY_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(BIOCHEMISTRY_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(BIOCHEMISTRY_2017, 130, 69, 48, 19, 29, 5, 6, 4, 4, NONE, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(BIOCHEMISTRY_2018, 130, 69, 48, 19, 29, 5, 6, 4, 4, NONE, 6, 2, 2, 60));

        // 식품영양학과
        wkuGraduationDatas.add(new WKUGraduationData(FOOD_NUTRITION_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FOOD_NUTRITION_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FOOD_NUTRITION_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FOOD_NUTRITION_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FOOD_NUTRITION_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FOOD_NUTRITION_2017, 130, 69, 48, 19, 29, 5, 6, 4, 4, NONE, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(FOOD_NUTRITION_2018, 130, 69, 48, 19, 29, 5, 6, 4, 4, NONE, 6, 2, 2, 60));

        // 한약학과
        wkuGraduationDatas.add(new WKUGraduationData(FOOD_NUTRITION_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FOOD_NUTRITION_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FOOD_NUTRITION_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FOOD_NUTRITION_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FOOD_NUTRITION_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FOOD_NUTRITION_2017, 160, NONE, NONE, NONE, 29, 5, 12, 2, 4, NONE, 2, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(FOOD_NUTRITION_2018, 160, NONE, NONE, NONE, 29, 5, 12, 2, 4, NONE, 2, 2, 2, 60));

        // 국어교육과
        wkuGraduationDatas.add(new WKUGraduationData(KOREAN_LANGUAGE_EDUCATION_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(KOREAN_LANGUAGE_EDUCATION_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(KOREAN_LANGUAGE_EDUCATION_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(KOREAN_LANGUAGE_EDUCATION_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(KOREAN_LANGUAGE_EDUCATION_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(KOREAN_LANGUAGE_EDUCATION_2017, 140, 69, 50, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(KOREAN_LANGUAGE_EDUCATION_2018, 140, 69, 50, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));

        // 영어교육과
        wkuGraduationDatas.add(new WKUGraduationData(ENGLISH_LANGUAGE_EDUCATION_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ENGLISH_LANGUAGE_EDUCATION_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ENGLISH_LANGUAGE_EDUCATION_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ENGLISH_LANGUAGE_EDUCATION_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ENGLISH_LANGUAGE_EDUCATION_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ENGLISH_LANGUAGE_EDUCATION_2017, 140, 69, 50, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(ENGLISH_LANGUAGE_EDUCATION_2018, 140, 69, 50, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));

        // 일어교육과
        wkuGraduationDatas.add(new WKUGraduationData(JAPANESE_LANGUAGE_EDUCATION_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(JAPANESE_LANGUAGE_EDUCATION_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(JAPANESE_LANGUAGE_EDUCATION_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(JAPANESE_LANGUAGE_EDUCATION_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(JAPANESE_LANGUAGE_EDUCATION_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(JAPANESE_LANGUAGE_EDUCATION_2017, 140, 69, 50, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(JAPANESE_LANGUAGE_EDUCATION_2018, 140, 69, 50, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));

        // 한문교육과
        wkuGraduationDatas.add(new WKUGraduationData(CHINESE_LANGUAGE_EDUCATION_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CHINESE_LANGUAGE_EDUCATION_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CHINESE_LANGUAGE_EDUCATION_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CHINESE_LANGUAGE_EDUCATION_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CHINESE_LANGUAGE_EDUCATION_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CHINESE_LANGUAGE_EDUCATION_2017, 140, 69, 50, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(CHINESE_LANGUAGE_EDUCATION_2018, 140, 69, 50, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));

        // 역사교육과
        wkuGraduationDatas.add(new WKUGraduationData(HISTORY_LANGUAGE_EDUCATION_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(HISTORY_LANGUAGE_EDUCATION_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(HISTORY_LANGUAGE_EDUCATION_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(HISTORY_LANGUAGE_EDUCATION_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(HISTORY_LANGUAGE_EDUCATION_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(HISTORY_LANGUAGE_EDUCATION_2017, 140, 69, 50, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(HISTORY_LANGUAGE_EDUCATION_2018, 140, 69, 50, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));

        // 교육학과
        wkuGraduationDatas.add(new WKUGraduationData(EDUCATION_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(EDUCATION_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(EDUCATION_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(EDUCATION_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(EDUCATION_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(EDUCATION_2017, 140, 69, 50, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(EDUCATION_2018, 140, 69, 50, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));

        // 유아교육과
        wkuGraduationDatas.add(new WKUGraduationData(CHILDHOOD_EDUCATION_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CHILDHOOD_EDUCATION_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CHILDHOOD_EDUCATION_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CHILDHOOD_EDUCATION_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CHILDHOOD_EDUCATION_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CHILDHOOD_EDUCATION_2017, 140, 69, 50, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(CHILDHOOD_EDUCATION_2018, 140, 69, 50, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));

        // 가정교육과
        wkuGraduationDatas.add(new WKUGraduationData(HOME_EDUCATION_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(HOME_EDUCATION_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(HOME_EDUCATION_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(HOME_EDUCATION_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(HOME_EDUCATION_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(HOME_EDUCATION_2017, 140, 69, 56, 19, 27, 5, 4, 4, 4, NONE, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(HOME_EDUCATION_2018, 140, 69, 56, 19, 27, 5, 4, 4, 4, NONE, 6, 2, 2, 60));

        // 체육교육과
        wkuGraduationDatas.add(new WKUGraduationData(PHYSICAL_EDUCATION_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(PHYSICAL_EDUCATION_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(PHYSICAL_EDUCATION_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(PHYSICAL_EDUCATION_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(PHYSICAL_EDUCATION_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(PHYSICAL_EDUCATION_2017, 140, 69, 56, 19, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(PHYSICAL_EDUCATION_2018, 140, 69, 56, 19, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));

        // 중등특수교육과
        wkuGraduationDatas.add(new WKUGraduationData(SECONDARY_SPECIAL_EDUCATION_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(SECONDARY_SPECIAL_EDUCATION_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(SECONDARY_SPECIAL_EDUCATION_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(SECONDARY_SPECIAL_EDUCATION_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(SECONDARY_SPECIAL_EDUCATION_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(SECONDARY_SPECIAL_EDUCATION_2017, 140, NONE, NONE, NONE, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(SECONDARY_SPECIAL_EDUCATION_2018, 140, NONE, NONE, NONE, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));

        // 수학교육과
        wkuGraduationDatas.add(new WKUGraduationData(MATHEMATICAL_EDUCATION_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MATHEMATICAL_EDUCATION_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MATHEMATICAL_EDUCATION_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MATHEMATICAL_EDUCATION_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MATHEMATICAL_EDUCATION_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MATHEMATICAL_EDUCATION_2017, 140, 69, 56, 19, 29, 5, 6, 4, 4, NONE, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(MATHEMATICAL_EDUCATION_2018, 140, 69, 56, 19, 27, 5, 6, 4, 4, 4, NONE, 2, 2, 60));

        // 수학정보통계학부
        wkuGraduationDatas.add(new WKUGraduationData(MATHEMATICAL_INFORMATION_STATISTICS_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MATHEMATICAL_INFORMATION_STATISTICS_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MATHEMATICAL_INFORMATION_STATISTICS_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MATHEMATICAL_INFORMATION_STATISTICS_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MATHEMATICAL_INFORMATION_STATISTICS_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MATHEMATICAL_INFORMATION_STATISTICS_2017, 130, 69, 48, 19, 39, 5, 16, 4, 4, NONE, 6, 2, 2, 60));

        // 응용수학부
        wkuGraduationDatas.add(new WKUGraduationData(APPLIED_MATHEMATICS_2018, 130, 69, 48, 19, 39, 5, 16, 4, 4, NONE, 6, 2, 2, 60));

        // 빅데이터 금융통계학부
        wkuGraduationDatas.add(new WKUGraduationData(BIGDATA_FININACIAL_STATISTICAL_2018, 130, 69, 48, 19, 39, 5, 16, 4, 4, NONE, 6, 2, 2, 60));

        // 바이오나노화학부
        wkuGraduationDatas.add(new WKUGraduationData(BIO_NANO_CHEMICAL_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(BIO_NANO_CHEMICAL_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(BIO_NANO_CHEMICAL_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(BIO_NANO_CHEMICAL_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(BIO_NANO_CHEMICAL_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(BIO_NANO_CHEMICAL_2017, 130, 69, 48, 19, 39, 5, 16, 4, 4, NONE, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(BIO_NANO_CHEMICAL_2018, 130, 69, 48, 19, 39, 5, 16, 4, 4, NONE, 6, 2, 2, 60));

        // 반도체디스플레이학부
        wkuGraduationDatas.add(new WKUGraduationData(SEMICONDUCTOR_DISPLAY_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(SEMICONDUCTOR_DISPLAY_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(SEMICONDUCTOR_DISPLAY_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(SEMICONDUCTOR_DISPLAY_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(SEMICONDUCTOR_DISPLAY_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(SEMICONDUCTOR_DISPLAY_2017, 130, 69, 48, 19, 39, 5, 16, 4, 4, NONE, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(SEMICONDUCTOR_DISPLAY_2018, 130, 69, 48, 19, 39, 5, 16, 4, 4, NONE, 6, 2, 2, 60));

        // 생명과학부
        wkuGraduationDatas.add(new WKUGraduationData(LIFE_SCIENCE_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(LIFE_SCIENCE_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(LIFE_SCIENCE_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(LIFE_SCIENCE_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(LIFE_SCIENCE_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(LIFE_SCIENCE_2017, 130, 69, 48, 19, 39, 5, 16, 4, 4, NONE, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(LIFE_SCIENCE_2018, 130, 69, 48, 19, 39, 5, 16, 4, 4, NONE, 6, 2, 2, 60));

        // 스포츠과학부
        wkuGraduationDatas.add(new WKUGraduationData(SPORTS_SCIENCE_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(SPORTS_SCIENCE_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(SPORTS_SCIENCE_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(SPORTS_SCIENCE_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(SPORTS_SCIENCE_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(SPORTS_SCIENCE_2017, 130, 66, 48, 19, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(SPORTS_SCIENCE_2018, 130, 66, 48, 19, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));

        // 뷰티디자인학부
        wkuGraduationDatas.add(new WKUGraduationData(BEAUTY_DESIGN_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(BEAUTY_DESIGN_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(BEAUTY_DESIGN_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(BEAUTY_DESIGN_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(BEAUTY_DESIGN_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(BEAUTY_DESIGN_2017, 130, 69, 48, 19, 29, 5, 6, 4, 4, NONE, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(BEAUTY_DESIGN_2018, 130, 69, 48, 19, 29, 5, 6, 4, 4, NONE, 6, 2, 2, 60));

        // 한의예과
        wkuGraduationDatas.add(new WKUGraduationData(ORIENTAL_TREATMENT_MEDICINE_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ORIENTAL_TREATMENT_MEDICINE_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ORIENTAL_TREATMENT_MEDICINE_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ORIENTAL_TREATMENT_MEDICINE_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ORIENTAL_TREATMENT_MEDICINE_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ORIENTAL_TREATMENT_MEDICINE_2017, 80, NONE, NONE, NONE, 27, 5, 12, 2, 4, NONE, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(ORIENTAL_TREATMENT_MEDICINE_2018, 80, NONE, NONE, NONE, 27, 5, 12, 2, 4, NONE, NONE, 2, 2, 60));

        // 한의학과
        wkuGraduationDatas.add(new WKUGraduationData(ORIENTAL_SCHOLARSHIP_MEDICINE_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ORIENTAL_SCHOLARSHIP_MEDICINE_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ORIENTAL_SCHOLARSHIP_MEDICINE_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ORIENTAL_SCHOLARSHIP_MEDICINE_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ORIENTAL_SCHOLARSHIP_MEDICINE_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ORIENTAL_SCHOLARSHIP_MEDICINE_2017, 160, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, 60));
        wkuGraduationDatas.add(new WKUGraduationData(ORIENTAL_SCHOLARSHIP_MEDICINE_2018, 160, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, 60));

        // 미술과
        wkuGraduationDatas.add(new WKUGraduationData(ART_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ART_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ART_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ART_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ART_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ART_2017, 130, 66, 48, 19, 27, 5, 4, 4, 4, NONE, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(ART_2018, 130, 66, 48, 19, 27, 5, 4, 4, 4, NONE, 6, 2, 2, 60));

        // 귀금속보석공예과
        wkuGraduationDatas.add(new WKUGraduationData(METALLIC_JEWELRY_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(METALLIC_JEWELRY_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(METALLIC_JEWELRY_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(METALLIC_JEWELRY_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(METALLIC_JEWELRY_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(METALLIC_JEWELRY_2017, 130, 66, 48, 19, 27, 5, 4, 4, 4, NONE, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(METALLIC_JEWELRY_2018, 130, 66, 48, 19, 27, 5, 4, 4, 4, NONE, 6, 2, 2, 60));

        // 디자인학부
        wkuGraduationDatas.add(new WKUGraduationData(DESIGN_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(DESIGN_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(DESIGN_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(DESIGN_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(DESIGN_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(DESIGN_2017, 130, 66, 48, 19, 27, 5, 4, 4, 4, NONE, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(DESIGN_2018, 130, 66, 48, 19, 27, 5, 4, 4, 4, NONE, 6, 2, 2, 60));

        // 패션디자인산업학과
        wkuGraduationDatas.add(new WKUGraduationData(FASHION_DESIGN_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FASHION_DESIGN_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FASHION_DESIGN_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FASHION_DESIGN_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FASHION_DESIGN_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FASHION_DESIGN_2017, 130, 69, 48, 19, 27, 5, 4, 4, 4, NONE, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(FASHION_DESIGN_2018, 130, 69, 48, 19, 27, 5, 4, 4, 4, NONE, 6, 2, 2, 60));

        // 행정언론학부
        wkuGraduationDatas.add(new WKUGraduationData(PUBLIC_ADMINISTRATION_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(PUBLIC_ADMINISTRATION_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(PUBLIC_ADMINISTRATION_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(PUBLIC_ADMINISTRATION_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(PUBLIC_ADMINISTRATION_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(PUBLIC_ADMINISTRATION_2017, 130, 66, 36, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(PUBLIC_ADMINISTRATION_2018, 130, 66, 36, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));

        // 복지보건학부(사회복지학)
        wkuGraduationDatas.add(new WKUGraduationData(SOCIAL_WELFARE_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(SOCIAL_WELFARE_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(SOCIAL_WELFARE_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(SOCIAL_WELFARE_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(SOCIAL_WELFARE_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(SOCIAL_WELFARE_2017, 130, 66, 42, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(SOCIAL_WELFARE_2018, 130, 66, 42, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));

        // 복지보건학부(보건행정학)
        wkuGraduationDatas.add(new WKUGraduationData(HEALTH_ADMINISTRATION_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(HEALTH_ADMINISTRATION_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(HEALTH_ADMINISTRATION_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(HEALTH_ADMINISTRATION_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(HEALTH_ADMINISTRATION_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(HEALTH_ADMINISTRATION_2017, 130, 66, 36, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(HEALTH_ADMINISTRATION_2018, 130, 66, 36, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));

        // 가정아동복지학과
        wkuGraduationDatas.add(new WKUGraduationData(CHILD_WELFARE_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CHILD_WELFARE_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CHILD_WELFARE_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CHILD_WELFARE_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CHILD_WELFARE_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CHILD_WELFARE_2017, 130, 69, 48, 19, 27, 5, 4, 4, 4, NONE, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(CHILD_WELFARE_2018, 130, 69, 48, 19, 27, 5, 4, 4, 4, NONE, 6, 2, 2, 60));

        // 군사학과
        wkuGraduationDatas.add(new WKUGraduationData(MILITARY_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MILITARY_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MILITARY_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MILITARY_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MILITARY_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MILITARY_2017, 130, 66, 42, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(MILITARY_2018, 130, 66, 42, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));

        // 경찰행정학과
        wkuGraduationDatas.add(new WKUGraduationData(POLICE_ADMINISTRATION_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(POLICE_ADMINISTRATION_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(POLICE_ADMINISTRATION_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(POLICE_ADMINISTRATION_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(POLICE_ADMINISTRATION_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(POLICE_ADMINISTRATION_2017, 130, 66, 36, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(POLICE_ADMINISTRATION_2018, 130, 66, 36, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));

        // 소방행정학과
        wkuGraduationDatas.add(new WKUGraduationData(FIRE_ADMINISTRATION_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FIRE_ADMINISTRATION_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FIRE_ADMINISTRATION_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FIRE_ADMINISTRATION_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FIRE_ADMINISTRATION_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(FIRE_ADMINISTRATION_2017, 130, 66, 36, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(FIRE_ADMINISTRATION_2018, 130, 66, 36, 15, 25, 5, 4, 4, 4, 4, NONE, 2, 2, 60));

        // 전기공학과
        wkuGraduationDatas.add(new WKUGraduationData(ELECTRICAL_ENGINEERING_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ELECTRICAL_ENGINEERING_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ELECTRICAL_ENGINEERING_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ELECTRICAL_ENGINEERING_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ELECTRICAL_ENGINEERING_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ELECTRICAL_ENGINEERING_2017, 136, 75, 48, 19, 40, 5, 15, 2, 4, 4, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(ELECTRICAL_ENGINEERING_2018, 136, 75, 48, 19, 40, 5, 15, 2, 4, 4, 6, 2, 2, 60));

        // 정보통신공학과
        wkuGraduationDatas.add(new WKUGraduationData(INFORMATION_COMMUNICATION_ENGINEERING_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(INFORMATION_COMMUNICATION_ENGINEERING_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(INFORMATION_COMMUNICATION_ENGINEERING_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(INFORMATION_COMMUNICATION_ENGINEERING_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(INFORMATION_COMMUNICATION_ENGINEERING_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(INFORMATION_COMMUNICATION_ENGINEERING_2017, 136, 75, 48, 19, 40, 5, 15, 2, 4, 4, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(INFORMATION_COMMUNICATION_ENGINEERING_2018, 136, 75, 48, 19, 40, 5, 15, 2, 4, 4, 6, 2, 2, 60));

        // 전자공학과
        wkuGraduationDatas.add(new WKUGraduationData(ELECTRONICAL_ENGINEERING_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ELECTRONICAL_ENGINEERING_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ELECTRONICAL_ENGINEERING_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ELECTRONICAL_ENGINEERING_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ELECTRONICAL_ENGINEERING_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ELECTRONICAL_ENGINEERING_2017, 136, 75, 48, 19, 40, 5, 15, 2, 4, 4, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(ELECTRONICAL_ENGINEERING_2018, 136, 75, 48, 19, 40, 5, 15, 2, 4, 4, 6, 2, 2, 60));

        // 전자융합공학과
        wkuGraduationDatas.add(new WKUGraduationData(ELECTRONIC_CONVERGENCE_ENGINEERING_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ELECTRONIC_CONVERGENCE_ENGINEERING_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ELECTRONIC_CONVERGENCE_ENGINEERING_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ELECTRONIC_CONVERGENCE_ENGINEERING_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ELECTRONIC_CONVERGENCE_ENGINEERING_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ELECTRONIC_CONVERGENCE_ENGINEERING_2017, 136, 75, 48, 19, 37, 5, 12, 2, 4, 4, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(ELECTRONIC_CONVERGENCE_ENGINEERING_2018, 136, 75, 48, 19, 37, 5, 12, 2, 4, 4, 6, 2, 2, 60));

        // 컴퓨터공학과 및 컴퓨터소프트웨어공학과
        wkuGraduationDatas.add(new WKUGraduationData(COMPUTER_ENGINEERING_2012, 136, 72, 48, 19, 31, 5, 12, 2, 4, NONE, NONE, NONE, NONE, 60));
        wkuGraduationDatas.add(new WKUGraduationData(COMPUTER_ENGINEERING_2013, 136, 72, 48, 19, 31, 5, 12, 2, 4, NONE, NONE, NONE, NONE, 60));
        wkuGraduationDatas.add(new WKUGraduationData(COMPUTER_ENGINEERING_2014, 136, 72, 48, 19, 31, 5, 12, 2, 4, NONE, NONE, NONE, NONE, 60));
        wkuGraduationDatas.add(new WKUGraduationData(COMPUTER_ENGINEERING_2015, 136, 72, 48, 19, 31, 5, 12, 2, 4, NONE, NONE, NONE, NONE, 60));
        wkuGraduationDatas.add(new WKUGraduationData(COMPUTER_ENGINEERING_2016, 136, 72, 48, 19, 31, 5, 12, 2, 4, NONE, NONE, NONE, NONE, 60));
        wkuGraduationDatas.add(new WKUGraduationData(COMPUTER_SOFTWARE_ENGINEERING_2017, 136, 75, 48, 19, 37, 5, 12, 2, 4, 4, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(COMPUTER_SOFTWARE_ENGINEERING_2018, 136, 75, 48, 19, 37, 5, 12, 2, 4, 4, 6, 2, 2, 60));

        // 디지털콘텐츠공학과
        wkuGraduationDatas.add(new WKUGraduationData(DIGITAL_CONTENT_ENGINEERING_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(DIGITAL_CONTENT_ENGINEERING_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(DIGITAL_CONTENT_ENGINEERING_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(DIGITAL_CONTENT_ENGINEERING_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(DIGITAL_CONTENT_ENGINEERING_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(DIGITAL_CONTENT_ENGINEERING_2017, 136, 75, 48, 19, 37, 5, 12, 2, 4, 4, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(DIGITAL_CONTENT_ENGINEERING_2018, 13, 75, 48, 19, 37, 5, 12, 2, 4, 4, 6, 2, 2, 60));

        // 기계공학과
        wkuGraduationDatas.add(new WKUGraduationData(MECHANICAL_ENGINEERING_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MECHANICAL_ENGINEERING_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MECHANICAL_ENGINEERING_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MECHANICAL_ENGINEERING_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MECHANICAL_ENGINEERING_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MECHANICAL_ENGINEERING_2017, 136, 75, 48, 19, 40, 5, 15, 2, 4, 4, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(MECHANICAL_ENGINEERING_2018, 136, 75, 48, 19, 40, 5, 15, 2, 4, 4, 6, 2, 2, 60));

        // 스마트자동차공학과
        wkuGraduationDatas.add(new WKUGraduationData(SMART_CAR_ENGINEERING_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(SMART_CAR_ENGINEERING_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(SMART_CAR_ENGINEERING_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(SMART_CAR_ENGINEERING_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(SMART_CAR_ENGINEERING_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(SMART_CAR_ENGINEERING_2017, 136, 75, 48, 19, 40, 5, 15, 2, 4, 4, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(SMART_CAR_ENGINEERING_2018, 136, 75, 48, 19, 40, 5, 15, 2, 4, 4, 6, 2, 2, 60));

        // 기계설계공학과
        wkuGraduationDatas.add(new WKUGraduationData(MECHANICAL_DESIGN_ENGINEERING_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MECHANICAL_DESIGN_ENGINEERING_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MECHANICAL_DESIGN_ENGINEERING_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MECHANICAL_DESIGN_ENGINEERING_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MECHANICAL_DESIGN_ENGINEERING_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MECHANICAL_DESIGN_ENGINEERING_2017, 136, 75, 48, 19, 40, 5, 15, 2, 4, 4, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(MECHANICAL_DESIGN_ENGINEERING_2018, 136, 75, 48, 19, 40, 5, 15, 2, 4, 4, 6, 2, 2, 60));

        // 건축공학과
        wkuGraduationDatas.add(new WKUGraduationData(ARCHITECTURAL_ENGINEERING_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ARCHITECTURAL_ENGINEERING_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ARCHITECTURAL_ENGINEERING_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ARCHITECTURAL_ENGINEERING_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ARCHITECTURAL_ENGINEERING_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ARCHITECTURAL_ENGINEERING_2017, 136, 75, 48, 19, 40, 5, 15, 2, 4, 4, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(ARCHITECTURAL_ENGINEERING_2018, 136, 75, 48, 19, 40, 5, 15, 2, 4, 4, 6, 2, 2, 60));

        // 도시공학부
        wkuGraduationDatas.add(new WKUGraduationData(URBAN_ENGINEERING_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(URBAN_ENGINEERING_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(URBAN_ENGINEERING_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(URBAN_ENGINEERING_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(URBAN_ENGINEERING_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(URBAN_ENGINEERING_2017, 136, 75, 48, 19, 31, 5, 6, 2, 4, 4, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(URBAN_ENGINEERING_2018, 136, 75, 48, 19, 31, 5, 6, 2, 4, 4, 6, 2, 2, 60));

        // 화학융합공학과
        wkuGraduationDatas.add(new WKUGraduationData(CHEMISTRY_CONVERGENCE_ENGINEERING_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CHEMISTRY_CONVERGENCE_ENGINEERING_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CHEMISTRY_CONVERGENCE_ENGINEERING_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CHEMISTRY_CONVERGENCE_ENGINEERING_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CHEMISTRY_CONVERGENCE_ENGINEERING_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CHEMISTRY_CONVERGENCE_ENGINEERING_2017, 136, 75, 48, 19, 40, 5, 15, 2, 4, 4, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(CHEMISTRY_CONVERGENCE_ENGINEERING_2018, 136, 75, 48, 19, 40, 5, 15, 2, 4, 4, 6, 2, 2, 60));

        // 탄소융합공학과
        wkuGraduationDatas.add(new WKUGraduationData(CARBON_CONVERGENCE_ENGINEERING_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CARBON_CONVERGENCE_ENGINEERING_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CARBON_CONVERGENCE_ENGINEERING_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CARBON_CONVERGENCE_ENGINEERING_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CARBON_CONVERGENCE_ENGINEERING_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CARBON_CONVERGENCE_ENGINEERING_2017, 136, 75, 48, 19, 40, 5, 15, 2, 4, 4, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(CARBON_CONVERGENCE_ENGINEERING_2018, 136, 75, 48, 19, 40, 5, 15, 2, 4, 4, 6, 2, 2, 60));

        // 건축학과
        wkuGraduationDatas.add(new WKUGraduationData(ARCHITECTURE_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ARCHITECTURE_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ARCHITECTURE_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ARCHITECTURE_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ARCHITECTURE_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(ARCHITECTURE_2017, 160, NONE, NONE, NONE, 33, 5, 8, 2, 4, 4, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(ARCHITECTURE_2018, 160, NONE, NONE, NONE, 33, 5, 8, 2, 4, 4, 6, 2, 2, 60));

        // 토목환경공학과
        wkuGraduationDatas.add(new WKUGraduationData(CIVIL_ENGINEERING_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CIVIL_ENGINEERING_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CIVIL_ENGINEERING_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CIVIL_ENGINEERING_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CIVIL_ENGINEERING_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(CIVIL_ENGINEERING_2017, 136, 75, 48, 19, 40, 5, 15, 2, 4, 4, 6, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(CIVIL_ENGINEERING_2018, 136, 75, 48, 19, 40, 5, 15, 2, 4, 4, 6, 2, 2, 60));

        // 치의예과
        wkuGraduationDatas.add(new WKUGraduationData(DENTISTRY_TREATMENT_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(DENTISTRY_TREATMENT_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(DENTISTRY_TREATMENT_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(DENTISTRY_TREATMENT_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(DENTISTRY_TREATMENT_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(DENTISTRY_TREATMENT_2017, 80, NONE, NONE, NONE, 27, 5, 12, 2, 4, NONE, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(DENTISTRY_TREATMENT_2018, 80, NONE, NONE, NONE, 27, 5, 12, 2, 4, NONE, NONE, 2, 2, 60));

        // 치의학과
        wkuGraduationDatas.add(new WKUGraduationData(DENTISTRY_SCHOLARSHIP_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(DENTISTRY_SCHOLARSHIP_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(DENTISTRY_SCHOLARSHIP_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(DENTISTRY_SCHOLARSHIP_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(DENTISTRY_SCHOLARSHIP_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(DENTISTRY_SCHOLARSHIP_2017, 160, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, 60));
        wkuGraduationDatas.add(new WKUGraduationData(DENTISTRY_SCHOLARSHIP_2018, 160, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, 60));

        // 의예과
        wkuGraduationDatas.add(new WKUGraduationData(MEDICAL_TREATMENT_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MEDICAL_TREATMENT_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MEDICAL_TREATMENT_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MEDICAL_TREATMENT_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MEDICAL_TREATMENT_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MEDICAL_TREATMENT_2017, 80, NONE, NONE, NONE, 27, 5, 12, 2, 4, NONE, NONE, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(MEDICAL_TREATMENT_2018, 80, NONE, NONE, NONE, 27, 5, 12, 2, 4, NONE, NONE, 2, 2, 60));

        // 의학과
        wkuGraduationDatas.add(new WKUGraduationData(MEDICAL_SCHOLARSHIP_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MEDICAL_SCHOLARSHIP_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MEDICAL_SCHOLARSHIP_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MEDICAL_SCHOLARSHIP_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MEDICAL_SCHOLARSHIP_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(MEDICAL_SCHOLARSHIP_2017, 160, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, 60));
        wkuGraduationDatas.add(new WKUGraduationData(MEDICAL_SCHOLARSHIP_2018, 160, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, 60));

        // 간호학과
        wkuGraduationDatas.add(new WKUGraduationData(NURSING_SCIENCE_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(NURSING_SCIENCE_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(NURSING_SCIENCE_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(NURSING_SCIENCE_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(NURSING_SCIENCE_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(NURSING_SCIENCE_2017, 140, NONE, NONE, NONE, 23, 5, 6, 2, 4, NONE, 2, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(NURSING_SCIENCE_2018, 140, NONE, NONE, NONE, 23, 5, 6, 2, 4, NONE, 2, 2, 2, 60));

        // 작업치료학과
        wkuGraduationDatas.add(new WKUGraduationData(OCCUPATIONAL_THERAPY_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(OCCUPATIONAL_THERAPY_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(OCCUPATIONAL_THERAPY_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(OCCUPATIONAL_THERAPY_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(OCCUPATIONAL_THERAPY_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(OCCUPATIONAL_THERAPY_2017, 140, NONE, NONE, NONE, 20, 5, 3, 2, 4, NONE, 2, 2, 2, 60));
        wkuGraduationDatas.add(new WKUGraduationData(OCCUPATIONAL_THERAPY_2018, 140, NONE, NONE, NONE, 20, 5, 3, 2, 4, NONE, 2, 2, 2, 60));

        // 약학과
        wkuGraduationDatas.add(new WKUGraduationData(PHARMACY_2012, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(PHARMACY_2013, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(PHARMACY_2014, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(PHARMACY_2015, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(PHARMACY_2016, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE));
        wkuGraduationDatas.add(new WKUGraduationData(PHARMACY_2017, 160, NONE, NONE, NONE, 3, 3, 0, 0, 0, 0, 0, 0, 0, 60));
        wkuGraduationDatas.add(new WKUGraduationData(PHARMACY_2018, 160, NONE, NONE, NONE, 3, 3, 0, 0, 0, 0, 0, 0, 0, 60));
    }

    private void createType() {
        Log.i("WKU", "WKUGraduation.createType / major : " + major + " studentNo : " + studentNo);
        switch(major) {
            case "원불교학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = WON_BUDDHISM_2012; break;
//                    case 2013 : this.TYPE = WON_BUDDHISM_2013; break;
//                    case 2014 : this.TYPE = WON_BUDDHISM_2014; break;
//                    case 2015 : this.TYPE = WON_BUDDHISM_2015; break;
//                    case 2016 : this.TYPE = WON_BUDDHISM_2016; break;
                    case 2017 : this.TYPE = WON_BUDDHISM_2017; break;
                    case 2018 : this.TYPE = WON_BUDDHISM_2018; break;
                }
                break;


            case "국어국문학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = KOREAN_LITERATURE_2012; break;
//                    case 2013 : this.TYPE = KOREAN_LITERATURE_2013; break;
//                    case 2014 : this.TYPE = KOREAN_LITERATURE_2014; break;
//                    case 2015 : this.TYPE = KOREAN_LITERATURE_2015; break;
//                    case 2016 : this.TYPE = KOREAN_LITERATURE_2016; break;
                    case 2017 : this.TYPE = KOREAN_LITERATURE_2017; break;
                    case 2018 : this.TYPE = KOREAN_LITERATURE_2018; break;
                }
                break;


            case "문예창작학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = CREATIVE_WRITING_2012; break;
//                    case 2013 : this.TYPE = CREATIVE_WRITING_2013; break;
//                    case 2014 : this.TYPE = CREATIVE_WRITING_2014; break;
//                    case 2015 : this.TYPE = CREATIVE_WRITING_2015; break;
//                    case 2016 : this.TYPE = CREATIVE_WRITING_2016; break;
                    case 2017 : this.TYPE = CREATIVE_WRITING_2017; break;
                    case 2018 : this.TYPE = CREATIVE_WRITING_2018; break;
                }
                break;

            case "영어영문학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = ENGLISH_LITERATURE_2012; break;
//                    case 2013 : this.TYPE = ENGLISH_LITERATURE_2013; break;
//                    case 2014 : this.TYPE = ENGLISH_LITERATURE_2014; break;
//                    case 2015 : this.TYPE = ENGLISH_LITERATURE_2015; break;
//                    case 2016 : this.TYPE = ENGLISH_LITERATURE_2016; break;
                    case 2017 : this.TYPE = ENGLISH_LITERATURE_2017; break;
                    case 2018 : this.TYPE = ENGLISH_LITERATURE_2018; break;
                }
                break;

            case "중국학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = CHINA_2012; break;
//                    case 2013 : this.TYPE = CHINA_2013; break;
//                    case 2014 : this.TYPE = CHINA_2014; break;
//                    case 2015 : this.TYPE = CHINA_2015; break;
//                    case 2016 : this.TYPE = CHINA_2016; break;
                    case 2017 : this.TYPE = CHINA_2017; break;
                    case 2018 : this.TYPE = CHINA_2018; break;
                }
                break;

            case "역사문화학부" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = HISTORY_CULTURE_2012; break;
//                    case 2013 : this.TYPE = HISTORY_CULTURE_2013; break;
//                    case 2014 : this.TYPE = HISTORY_CULTURE_2014; break;
//                    case 2015 : this.TYPE = HISTORY_CULTURE_2015; break;
//                    case 2016 : this.TYPE = HISTORY_CULTURE_2016; break;
                    case 2017 : this.TYPE = HISTORY_CULTURE_2017; break;
                    case 2018 : this.TYPE = HISTORY_CULTURE_2018; break;
                }
                break;

            case "철학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = PHILOSOPHY_2012; break;
//                    case 2013 : this.TYPE = PHILOSOPHY_2013; break;
//                    case 2014 : this.TYPE = PHILOSOPHY_2014; break;
//                    case 2015 : this.TYPE = PHILOSOPHY_2015; break;
//                    case 2016 : this.TYPE = PHILOSOPHY_2016; break;
                    case 2017 : this.TYPE = PHILOSOPHY_2017; break;
                    case 2018 : this.TYPE = PHILOSOPHY_2018; break;
                }
                break;

            case "음악과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = MUSIC_2012; break;
//                    case 2013 : this.TYPE = MUSIC_2013; break;
//                    case 2014 : this.TYPE = MUSIC_2014; break;
//                    case 2015 : this.TYPE = MUSIC_2015; break;
//                    case 2016 : this.TYPE = MUSIC_2016; break;
                    case 2017 : this.TYPE = MUSIC_2017; break;
                    case 2018 : this.TYPE = MUSIC_2018; break;
                }
                break;

            case "경영학부" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = BUSINESS_2012; break;
//                    case 2013 : this.TYPE = BUSINESS_2013; break;
//                    case 2014 : this.TYPE = BUSINESS_2014; break;
//                    case 2015 : this.TYPE = BUSINESS_2015; break;
//                    case 2016 : this.TYPE = BUSINESS_2016; break;
                    case 2017 : this.TYPE = BUSINESS_2017; break;
                    case 2018 : this.TYPE = BUSINESS_2018; break;
                }
                break;

            case "경제학부" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = ECONOMY_2012; break;
//                    case 2013 : this.TYPE = ECONOMY_2013; break;
//                    case 2014 : this.TYPE = ECONOMY_2014; break;
//                    case 2015 : this.TYPE = ECONOMY_2015; break;
//                    case 2016 : this.TYPE = ECONOMY_2016; break;
                    case 2017 : this.TYPE = ECONOMY_2017; break;
                    case 2018 : this.TYPE = ECONOMY_2018; break;
                }
                break;

            case "국제통상학부" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = INTERNATIONAL_TRADE_2012; break;
//                    case 2013 : this.TYPE = INTERNATIONAL_TRADE_2013; break;
//                    case 2014 : this.TYPE = INTERNATIONAL_TRADE_2014; break;
//                    case 2015 : this.TYPE = INTERNATIONAL_TRADE_2015; break;
//                    case 2016 : this.TYPE = INTERNATIONAL_TRADE_2016; break;
                    case 2017 : this.TYPE = INTERNATIONAL_TRADE_2017; break;
                    case 2018 : this.TYPE = INTERNATIONAL_TRADE_2018; break;
                }
                break;

            case "원예산업학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = HORTICULTURE_2012; break;
//                    case 2013 : this.TYPE = HORTICULTURE_2013; break;
//                    case 2014 : this.TYPE = HORTICULTURE_2014; break;
//                    case 2015 : this.TYPE = HORTICULTURE_2015; break;
//                    case 2016 : this.TYPE = HORTICULTURE_2016; break;
                    case 2017 : this.TYPE = HORTICULTURE_2017; break;
                    case 2018 : this.TYPE = HORTICULTURE_2018; break;
                }
                break;

            case "산림조경학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = FOREST_LANDSCAPE_2012; break;
//                    case 2013 : this.TYPE = FOREST_LANDSCAPE_2013; break;
//                    case 2014 : this.TYPE = FOREST_LANDSCAPE_2014; break;
//                    case 2015 : this.TYPE = FOREST_LANDSCAPE_2015; break;
//                    case 2016 : this.TYPE = FOREST_LANDSCAPE_2016; break;
                    case 2017 : this.TYPE = FOREST_LANDSCAPE_2017; break;
                    case 2018 : this.TYPE = FOREST_LANDSCAPE_2018; break;
                }
                break;

            case "식품생명공학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = FOOD_LIFE_ENGINEERING_2012; break;
//                    case 2013 : this.TYPE = FOOD_LIFE_ENGINEERING_2013; break;
//                    case 2014 : this.TYPE = FOOD_LIFE_ENGINEERING_2014; break;
//                    case 2015 : this.TYPE = FOOD_LIFE_ENGINEERING_2015; break;
//                    case 2016 : this.TYPE = FOOD_LIFE_ENGINEERING_2016; break;
                    case 2017 : this.TYPE = FOOD_LIFE_ENGINEERING_2017; break;
                    case 2018 : this.TYPE = FOOD_LIFE_ENGINEERING_2018; break;
                }
                break;

            case "생물환경화학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = BIOCHEMISTRY_2012; break;
//                    case 2013 : this.TYPE = BIOCHEMISTRY_2013; break;
//                    case 2014 : this.TYPE = BIOCHEMISTRY_2014; break;
//                    case 2015 : this.TYPE = BIOCHEMISTRY_2015; break;
//                    case 2016 : this.TYPE = BIOCHEMISTRY_2016; break;
                    case 2017 : this.TYPE = BIOCHEMISTRY_2017; break;
                    case 2018 : this.TYPE = BIOCHEMISTRY_2018; break;
                }
                break;

            case "식품영양학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = FOOD_NUTRITION_2012; break;
//                    case 2013 : this.TYPE = FOOD_NUTRITION_2013; break;
//                    case 2014 : this.TYPE = FOOD_NUTRITION_2014; break;
//                    case 2015 : this.TYPE = FOOD_NUTRITION_2015; break;
//                    case 2016 : this.TYPE = FOOD_NUTRITION_2016; break;
                    case 2017 : this.TYPE = FOOD_NUTRITION_2017; break;
                    case 2018 : this.TYPE = FOOD_NUTRITION_2018; break;
                }
                break;

            case "한약학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = ORIENTAL_MEDICINE_2012; break;
//                    case 2013 : this.TYPE = ORIENTAL_MEDICINE_2013; break;
//                    case 2014 : this.TYPE = ORIENTAL_MEDICINE_2014; break;
//                    case 2015 : this.TYPE = ORIENTAL_MEDICINE_2015; break;
//                    case 2016 : this.TYPE = ORIENTAL_MEDICINE_2016; break;
                    case 2017 : this.TYPE = ORIENTAL_MEDICINE_2017; break;
                    case 2018 : this.TYPE = ORIENTAL_MEDICINE_2018; break;
                }
                break;

            case "국어교육과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = KOREAN_LANGUAGE_EDUCATION_2012; break;
//                    case 2013 : this.TYPE = KOREAN_LANGUAGE_EDUCATION_2013; break;
//                    case 2014 : this.TYPE = KOREAN_LANGUAGE_EDUCATION_2014; break;
//                    case 2015 : this.TYPE = KOREAN_LANGUAGE_EDUCATION_2015; break;
//                    case 2016 : this.TYPE = KOREAN_LANGUAGE_EDUCATION_2016; break;
                    case 2017 : this.TYPE = KOREAN_LANGUAGE_EDUCATION_2017; break;
                    case 2018 : this.TYPE = KOREAN_LANGUAGE_EDUCATION_2018; break;
                }
                break;

            case "영어교육과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = ENGLISH_LANGUAGE_EDUCATION_2012; break;
//                    case 2013 : this.TYPE = ENGLISH_LANGUAGE_EDUCATION_2013; break;
//                    case 2014 : this.TYPE = ENGLISH_LANGUAGE_EDUCATION_2014; break;
//                    case 2015 : this.TYPE = ENGLISH_LANGUAGE_EDUCATION_2015; break;
//                    case 2016 : this.TYPE = ENGLISH_LANGUAGE_EDUCATION_2016; break;
                    case 2017 : this.TYPE = ENGLISH_LANGUAGE_EDUCATION_2017; break;
                    case 2018 : this.TYPE = ENGLISH_LANGUAGE_EDUCATION_2018; break;
                }
                break;

            case "일어교육과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = JAPANESE_LANGUAGE_EDUCATION_2012; break;
//                    case 2013 : this.TYPE = JAPANESE_LANGUAGE_EDUCATION_2013; break;
//                    case 2014 : this.TYPE = JAPANESE_LANGUAGE_EDUCATION_2014; break;
//                    case 2015 : this.TYPE = JAPANESE_LANGUAGE_EDUCATION_2015; break;
//                    case 2016 : this.TYPE = JAPANESE_LANGUAGE_EDUCATION_2016; break;
                    case 2017 : this.TYPE = JAPANESE_LANGUAGE_EDUCATION_2017; break;
                    case 2018 : this.TYPE = JAPANESE_LANGUAGE_EDUCATION_2018; break;
                }
                break;

            case "한문교육과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = CHINESE_LANGUAGE_EDUCATION_2012; break;
//                    case 2013 : this.TYPE = CHINESE_LANGUAGE_EDUCATION_2013; break;
//                    case 2014 : this.TYPE = CHINESE_LANGUAGE_EDUCATION_2014; break;
//                    case 2015 : this.TYPE = CHINESE_LANGUAGE_EDUCATION_2015; break;
//                    case 2016 : this.TYPE = CHINESE_LANGUAGE_EDUCATION_2016; break;
                    case 2017 : this.TYPE = CHINESE_LANGUAGE_EDUCATION_2017; break;
                    case 2018 : this.TYPE = CHINESE_LANGUAGE_EDUCATION_2018; break;
                }
                break;

            case "역사교육과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = HISTORY_LANGUAGE_EDUCATION_2012; break;
//                    case 2013 : this.TYPE = HISTORY_LANGUAGE_EDUCATION_2013; break;
//                    case 2014 : this.TYPE = HISTORY_LANGUAGE_EDUCATION_2014; break;
//                    case 2015 : this.TYPE = HISTORY_LANGUAGE_EDUCATION_2015; break;
//                    case 2016 : this.TYPE = HISTORY_LANGUAGE_EDUCATION_2016; break;
                    case 2017 : this.TYPE = HISTORY_LANGUAGE_EDUCATION_2017; break;
                    case 2018 : this.TYPE = HISTORY_LANGUAGE_EDUCATION_2018; break;
                }
                break;

            case "교육학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = EDUCATION_2012; break;
//                    case 2013 : this.TYPE = EDUCATION_2013; break;
//                    case 2014 : this.TYPE = EDUCATION_2014; break;
//                    case 2015 : this.TYPE = EDUCATION_2015; break;
//                    case 2016 : this.TYPE = EDUCATION_2016; break;
                    case 2017 : this.TYPE = EDUCATION_2017; break;
                    case 2018 : this.TYPE = EDUCATION_2018; break;
                }
                break;

            case "유아교육과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = CHILDHOOD_EDUCATION_2012; break;
//                    case 2013 : this.TYPE = CHILDHOOD_EDUCATION_2013; break;
//                    case 2014 : this.TYPE = CHILDHOOD_EDUCATION_2014; break;
//                    case 2015 : this.TYPE = CHILDHOOD_EDUCATION_2015; break;
//                    case 2016 : this.TYPE = CHILDHOOD_EDUCATION_2016; break;
                    case 2017 : this.TYPE = CHILDHOOD_EDUCATION_2017; break;
                    case 2018 : this.TYPE = CHILDHOOD_EDUCATION_2018; break;
                }
                break;

            case "가정교육과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = HOME_EDUCATION_2012; break;
//                    case 2013 : this.TYPE = HOME_EDUCATION_2013; break;
//                    case 2014 : this.TYPE = HOME_EDUCATION_2014; break;
//                    case 2015 : this.TYPE = HOME_EDUCATION_2015; break;
//                    case 2016 : this.TYPE = HOME_EDUCATION_2016; break;
                    case 2017 : this.TYPE = HOME_EDUCATION_2017; break;
                    case 2018 : this.TYPE = HOME_EDUCATION_2018; break;
                }
                break;

            case "체육교육과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = PHYSICAL_EDUCATION_2012; break;
//                    case 2013 : this.TYPE = PHYSICAL_EDUCATION_2013; break;
//                    case 2014 : this.TYPE = PHYSICAL_EDUCATION_2014; break;
//                    case 2015 : this.TYPE = PHYSICAL_EDUCATION_2015; break;
//                    case 2016 : this.TYPE = PHYSICAL_EDUCATION_2016; break;
                    case 2017 : this.TYPE = PHYSICAL_EDUCATION_2017; break;
                    case 2018 : this.TYPE = PHYSICAL_EDUCATION_2018; break;
                }
                break;

            case "중등특수교육과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = SECONDARY_SPECIAL_EDUCATION_2012; break;
//                    case 2013 : this.TYPE = SECONDARY_SPECIAL_EDUCATION_2013; break;
//                    case 2014 : this.TYPE = SECONDARY_SPECIAL_EDUCATION_2014; break;
//                    case 2015 : this.TYPE = SECONDARY_SPECIAL_EDUCATION_2015; break;
//                    case 2016 : this.TYPE = SECONDARY_SPECIAL_EDUCATION_2016; break;
                    case 2017 : this.TYPE = SECONDARY_SPECIAL_EDUCATION_2017; break;
                    case 2018 : this.TYPE = SECONDARY_SPECIAL_EDUCATION_2018; break;
                }
                break;

            case "수학교육과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = MATHEMATICAL_EDUCATION_2012; break;
//                    case 2013 : this.TYPE = MATHEMATICAL_EDUCATION_2013; break;
//                    case 2014 : this.TYPE = MATHEMATICAL_EDUCATION_2014; break;
//                    case 2015 : this.TYPE = MATHEMATICAL_EDUCATION_2015; break;
//                    case 2016 : this.TYPE = MATHEMATICAL_EDUCATION_2016; break;
                    case 2017 : this.TYPE = MATHEMATICAL_EDUCATION_2017; break;
                    case 2018 : this.TYPE = MATHEMATICAL_EDUCATION_2018; break;
                }
                break;

            case "수학정보통계학부" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = MATHEMATICAL_INFORMATION_STATISTICS_2012; break;
//                    case 2013 : this.TYPE = MATHEMATICAL_INFORMATION_STATISTICS_2013; break;
//                    case 2014 : this.TYPE = MATHEMATICAL_INFORMATION_STATISTICS_2014; break;
//                    case 2015 : this.TYPE = MATHEMATICAL_INFORMATION_STATISTICS_2015; break;
//                    case 2016 : this.TYPE = MATHEMATICAL_INFORMATION_STATISTICS_2016; break;
                    case 2017 : this.TYPE = MATHEMATICAL_INFORMATION_STATISTICS_2017; break;
                }
                break;

            case "응용수학부" :
                switch(studentNo) {
                    case 2018 : this.TYPE = APPLIED_MATHEMATICS_2018; break;
                }
                break;

            case "빅데이터금융통계학부" :
                switch(studentNo) {
                    case 2018 : this.TYPE = BIGDATA_FININACIAL_STATISTICAL_2018; break;
                }
                break;

            case "바이오나노화학부" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = BIO_NANO_CHEMICAL_2012; break;
//                    case 2013 : this.TYPE = BIO_NANO_CHEMICAL_2013; break;
//                    case 2014 : this.TYPE = BIO_NANO_CHEMICAL_2014; break;
//                    case 2015 : this.TYPE = BIO_NANO_CHEMICAL_2015; break;
//                    case 2016 : this.TYPE = BIO_NANO_CHEMICAL_2016; break;
                    case 2017 : this.TYPE = BIO_NANO_CHEMICAL_2017; break;
                    case 2018 : this.TYPE = BIO_NANO_CHEMICAL_2018; break;
                }
                break;

            case "반도체디스플레이학부" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = SEMICONDUCTOR_DISPLAY_2012; break;
//                    case 2013 : this.TYPE = SEMICONDUCTOR_DISPLAY_2013; break;
//                    case 2014 : this.TYPE = SEMICONDUCTOR_DISPLAY_2014; break;
//                    case 2015 : this.TYPE = SEMICONDUCTOR_DISPLAY_2015; break;
//                    case 2016 : this.TYPE = SEMICONDUCTOR_DISPLAY_2016; break;
                    case 2017 : this.TYPE = SEMICONDUCTOR_DISPLAY_2017; break;
                    case 2018 : this.TYPE = SEMICONDUCTOR_DISPLAY_2018; break;
                }
                break;

            case "생명과학부" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = LIFE_SCIENCE_2012; break;
//                    case 2013 : this.TYPE = LIFE_SCIENCE_2013; break;
//                    case 2014 : this.TYPE = LIFE_SCIENCE_2014; break;
//                    case 2015 : this.TYPE = LIFE_SCIENCE_2015; break;
//                    case 2016 : this.TYPE = LIFE_SCIENCE_2016; break;
                    case 2017 : this.TYPE = LIFE_SCIENCE_2017; break;
                    case 2018 : this.TYPE = LIFE_SCIENCE_2018; break;
                }
                break;

            case "스포츠과학부" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = SPORTS_SCIENCE_2012; break;
//                    case 2013 : this.TYPE = SPORTS_SCIENCE_2013; break;
//                    case 2014 : this.TYPE = SPORTS_SCIENCE_2014; break;
//                    case 2015 : this.TYPE = SPORTS_SCIENCE_2015; break;
//                    case 2016 : this.TYPE = SPORTS_SCIENCE_2016; break;
                    case 2017 : this.TYPE = SPORTS_SCIENCE_2017; break;
                    case 2018 : this.TYPE = SPORTS_SCIENCE_2018; break;
                }
                break;

            case "뷰티디자인학부" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = BEAUTY_DESIGN_2012; break;
//                    case 2013 : this.TYPE = BEAUTY_DESIGN_2013; break;
//                    case 2014 : this.TYPE = BEAUTY_DESIGN_2014; break;
//                    case 2015 : this.TYPE = BEAUTY_DESIGN_2015; break;
//                    case 2016 : this.TYPE = BEAUTY_DESIGN_2016; break;
                    case 2017 : this.TYPE = BEAUTY_DESIGN_2017; break;
                    case 2018 : this.TYPE = BEAUTY_DESIGN_2018; break;
                }
                break;

            case "한의예과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = ORIENTAL_TREATMENT_MEDICINE_2012; break;
//                    case 2013 : this.TYPE = ORIENTAL_TREATMENT_MEDICINE_2013; break;
//                    case 2014 : this.TYPE = ORIENTAL_TREATMENT_MEDICINE_2014; break;
//                    case 2015 : this.TYPE = ORIENTAL_TREATMENT_MEDICINE_2015; break;
//                    case 2016 : this.TYPE = ORIENTAL_TREATMENT_MEDICINE_2016; break;
                    case 2017 : this.TYPE = ORIENTAL_TREATMENT_MEDICINE_2017; break;
                    case 2018 : this.TYPE = ORIENTAL_TREATMENT_MEDICINE_2018; break;
                }
                break;

            case "한의학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = ORIENTAL_SCHOLARSHIP_MEDICINE_2012; break;
//                    case 2013 : this.TYPE = ORIENTAL_SCHOLARSHIP_MEDICINE_2013; break;
//                    case 2014 : this.TYPE = ORIENTAL_SCHOLARSHIP_MEDICINE_2014; break;
//                    case 2015 : this.TYPE = ORIENTAL_SCHOLARSHIP_MEDICINE_2015; break;
//                    case 2016 : this.TYPE = ORIENTAL_SCHOLARSHIP_MEDICINE_2016; break;
                    case 2017 : this.TYPE = ORIENTAL_SCHOLARSHIP_MEDICINE_2017; break;
                    case 2018 : this.TYPE = ORIENTAL_SCHOLARSHIP_MEDICINE_2018; break;
                }
                break;

            case "미술과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = ART_2012; break;
//                    case 2013 : this.TYPE = ART_2013; break;
//                    case 2014 : this.TYPE = ART_2014; break;
//                    case 2015 : this.TYPE = ART_2015; break;
//                    case 2016 : this.TYPE = ART_2016; break;
                    case 2017 : this.TYPE = ART_2017; break;
                    case 2018 : this.TYPE = ART_2018; break;
                }
                break;

            case "귀금속보석공예과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = METALLIC_JEWELRY_2012; break;
//                    case 2013 : this.TYPE = METALLIC_JEWELRY_2013; break;
//                    case 2014 : this.TYPE = METALLIC_JEWELRY_2014; break;
//                    case 2015 : this.TYPE = METALLIC_JEWELRY_2015; break;
//                    case 2016 : this.TYPE = METALLIC_JEWELRY_2016; break;
                    case 2017 : this.TYPE = METALLIC_JEWELRY_2017; break;
                    case 2018 : this.TYPE = METALLIC_JEWELRY_2018; break;
                }
                break;

            case "디자인학부" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = DESIGN_2012; break;
//                    case 2013 : this.TYPE = DESIGN_2013; break;
//                    case 2014 : this.TYPE = DESIGN_2014; break;
//                    case 2015 : this.TYPE = DESIGN_2015; break;
//                    case 2016 : this.TYPE = DESIGN_2016; break;
                    case 2017 : this.TYPE = DESIGN_2017; break;
                    case 2018 : this.TYPE = DESIGN_2018; break;
                }
                break;

            case "패션디자인산업학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = FASHION_DESIGN_2012; break;
//                    case 2013 : this.TYPE = FASHION_DESIGN_2013; break;
//                    case 2014 : this.TYPE = FASHION_DESIGN_2014; break;
//                    case 2015 : this.TYPE = FASHION_DESIGN_2015; break;
//                    case 2016 : this.TYPE = FASHION_DESIGN_2016; break;
                    case 2017 : this.TYPE = FASHION_DESIGN_2017; break;
                    case 2018 : this.TYPE = FASHION_DESIGN_2018; break;
                }
                break;

            case "행정언론학부" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = PUBLIC_ADMINISTRATION_2012; break;
//                    case 2013 : this.TYPE = PUBLIC_ADMINISTRATION_2013; break;
//                    case 2014 : this.TYPE = PUBLIC_ADMINISTRATION_2014; break;
//                    case 2015 : this.TYPE = PUBLIC_ADMINISTRATION_2015; break;
//                    case 2016 : this.TYPE = PUBLIC_ADMINISTRATION_2016; break;
                    case 2017 : this.TYPE = PUBLIC_ADMINISTRATION_2017; break;
                    case 2018 : this.TYPE = PUBLIC_ADMINISTRATION_2018; break;
                }
                break;

            case "복지보건학부(사회복지학)" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = SOCIAL_WELFARE_2012; break;
//                    case 2013 : this.TYPE = SOCIAL_WELFARE_2013; break;
//                    case 2014 : this.TYPE = SOCIAL_WELFARE_2014; break;
//                    case 2015 : this.TYPE = SOCIAL_WELFARE_2015; break;
//                    case 2016 : this.TYPE = SOCIAL_WELFARE_2016; break;
                    case 2017 : this.TYPE = SOCIAL_WELFARE_2017; break;
                    case 2018 : this.TYPE = SOCIAL_WELFARE_2018; break;
                }
                break;

            case "복지보건학부(보건행정학)" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = HEALTH_ADMINISTRATION_2012; break;
//                    case 2013 : this.TYPE = HEALTH_ADMINISTRATION_2013; break;
//                    case 2014 : this.TYPE = HEALTH_ADMINISTRATION_2014; break;
//                    case 2015 : this.TYPE = HEALTH_ADMINISTRATION_2015; break;
//                    case 2016 : this.TYPE = HEALTH_ADMINISTRATION_2016; break;
                    case 2017 : this.TYPE = HEALTH_ADMINISTRATION_2017; break;
                    case 2018 : this.TYPE = HEALTH_ADMINISTRATION_2018; break;
                }
                break;

            case "가정아동복지학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = CHILD_WELFARE_2012; break;
//                    case 2013 : this.TYPE = CHILD_WELFARE_2013; break;
//                    case 2014 : this.TYPE = CHILD_WELFARE_2014; break;
//                    case 2015 : this.TYPE = CHILD_WELFARE_2015; break;
//                    case 2016 : this.TYPE = CHILD_WELFARE_2016; break;
                    case 2017 : this.TYPE = CHILD_WELFARE_2017; break;
                    case 2018 : this.TYPE = CHILD_WELFARE_2018; break;
                }
                break;

            case "군사학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = MILITARY_2012; break;
//                    case 2013 : this.TYPE = MILITARY_2013; break;
//                    case 2014 : this.TYPE = MILITARY_2014; break;
//                    case 2015 : this.TYPE = MILITARY_2015; break;
//                    case 2016 : this.TYPE = MILITARY_2016; break;
                    case 2017 : this.TYPE = MILITARY_2017; break;
                    case 2018 : this.TYPE = MILITARY_2018; break;
                }
                break;

            case "경찰행정학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = POLICE_ADMINISTRATION_2012; break;
//                    case 2013 : this.TYPE = POLICE_ADMINISTRATION_2013; break;
//                    case 2014 : this.TYPE = POLICE_ADMINISTRATION_2014; break;
//                    case 2015 : this.TYPE = POLICE_ADMINISTRATION_2015; break;
//                    case 2016 : this.TYPE = POLICE_ADMINISTRATION_2016; break;
                    case 2017 : this.TYPE = POLICE_ADMINISTRATION_2017; break;
                    case 2018 : this.TYPE = POLICE_ADMINISTRATION_2018; break;
                }
                break;

            case "소방행정학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = FIRE_ADMINISTRATION_2012; break;
//                    case 2013 : this.TYPE = FIRE_ADMINISTRATION_2013; break;
//                    case 2014 : this.TYPE = FIRE_ADMINISTRATION_2014; break;
//                    case 2015 : this.TYPE = FIRE_ADMINISTRATION_2015; break;
//                    case 2016 : this.TYPE = FIRE_ADMINISTRATION_2016; break;
                    case 2017 : this.TYPE = FIRE_ADMINISTRATION_2017; break;
                    case 2018 : this.TYPE = FIRE_ADMINISTRATION_2018; break;
                }
                break;

            case "전기공학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = ELECTRICAL_ENGINEERING_2012; break;
//                    case 2013 : this.TYPE = ELECTRICAL_ENGINEERING_2013; break;
//                    case 2014 : this.TYPE = ELECTRICAL_ENGINEERING_2014; break;
//                    case 2015 : this.TYPE = ELECTRICAL_ENGINEERING_2015; break;
//                    case 2016 : this.TYPE = ELECTRICAL_ENGINEERING_2016; break;
                    case 2017 : this.TYPE = ELECTRICAL_ENGINEERING_2017; break;
                    case 2018 : this.TYPE = ELECTRICAL_ENGINEERING_2018; break;
                }
                break;

            case "정보통신공학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = INFORMATION_COMMUNICATION_ENGINEERING_2012; break;
//                    case 2013 : this.TYPE = INFORMATION_COMMUNICATION_ENGINEERING_2013; break;
//                    case 2014 : this.TYPE = INFORMATION_COMMUNICATION_ENGINEERING_2014; break;
//                    case 2015 : this.TYPE = INFORMATION_COMMUNICATION_ENGINEERING_2015; break;
//                    case 2016 : this.TYPE = INFORMATION_COMMUNICATION_ENGINEERING_2016; break;
                    case 2017 : this.TYPE = INFORMATION_COMMUNICATION_ENGINEERING_2017; break;
                    case 2018 : this.TYPE = INFORMATION_COMMUNICATION_ENGINEERING_2018; break;
                }
                break;

            case "전자공학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = ELECTRONICAL_ENGINEERING_2012; break;
//                    case 2013 : this.TYPE = ELECTRONICAL_ENGINEERING_2013; break;
//                    case 2014 : this.TYPE = ELECTRONICAL_ENGINEERING_2014; break;
//                    case 2015 : this.TYPE = ELECTRONICAL_ENGINEERING_2015; break;
//                    case 2016 : this.TYPE = ELECTRONICAL_ENGINEERING_2016; break;
                    case 2017 : this.TYPE = ELECTRONICAL_ENGINEERING_2017; break;
                    case 2018 : this.TYPE = ELECTRONICAL_ENGINEERING_2018; break;
                }
                break;

            case "전자융합공학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = ELECTRONIC_CONVERGENCE_ENGINEERING_2012; break;
//                    case 2013 : this.TYPE = ELECTRONIC_CONVERGENCE_ENGINEERING_2013; break;
//                    case 2014 : this.TYPE = ELECTRONIC_CONVERGENCE_ENGINEERING_2014; break;
//                    case 2015 : this.TYPE = ELECTRONIC_CONVERGENCE_ENGINEERING_2015; break;
//                    case 2016 : this.TYPE = ELECTRONIC_CONVERGENCE_ENGINEERING_2016; break;
                    case 2017 : this.TYPE = ELECTRONIC_CONVERGENCE_ENGINEERING_2017; break;
                    case 2018 : this.TYPE = ELECTRONIC_CONVERGENCE_ENGINEERING_2018; break;
                }
                break;

            case "컴퓨터공학과" :
                switch(studentNo) {
                    case 2012 : this.TYPE = COMPUTER_ENGINEERING_2012; break;
                    case 2013 : this.TYPE = COMPUTER_ENGINEERING_2013; break;
                    case 2014 : this.TYPE = COMPUTER_ENGINEERING_2014; break;
                    case 2015 : this.TYPE = COMPUTER_ENGINEERING_2015; break;
                    case 2016 : this.TYPE = COMPUTER_ENGINEERING_2016; break;
                }
                break;

            case "컴퓨터·소프트웨어계열" :
                switch(studentNo) {
                    case 2017 : this.TYPE = COMPUTER_SOFTWARE_ENGINEERING_2017; break;
                    case 2018 : this.TYPE = COMPUTER_SOFTWARE_ENGINEERING_2018; break;
                }
                break;

            case "디지털콘텐츠공학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = DIGITAL_CONTENT_ENGINEERING_2012; break;
//                    case 2013 : this.TYPE = DIGITAL_CONTENT_ENGINEERING_2013; break;
//                    case 2014 : this.TYPE = DIGITAL_CONTENT_ENGINEERING_2014; break;
//                    case 2015 : this.TYPE = DIGITAL_CONTENT_ENGINEERING_2015; break;
//                    case 2016 : this.TYPE = DIGITAL_CONTENT_ENGINEERING_2016; break;
                    case 2017 : this.TYPE = DIGITAL_CONTENT_ENGINEERING_2017; break;
                    case 2018 : this.TYPE = DIGITAL_CONTENT_ENGINEERING_2018; break;
                }
                break;

            case "기계공학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = MECHANICAL_ENGINEERING_2012; break;
//                    case 2013 : this.TYPE = MECHANICAL_ENGINEERING_2013; break;
//                    case 2014 : this.TYPE = MECHANICAL_ENGINEERING_2014; break;
//                    case 2015 : this.TYPE = MECHANICAL_ENGINEERING_2015; break;
//                    case 2016 : this.TYPE = MECHANICAL_ENGINEERING_2016; break;
                    case 2017 : this.TYPE = MECHANICAL_ENGINEERING_2017; break;
                    case 2018 : this.TYPE = MECHANICAL_ENGINEERING_2018; break;
                }
                break;

            case "스마트자동차공학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = SMART_CAR_ENGINEERING_2012; break;
//                    case 2013 : this.TYPE = SMART_CAR_ENGINEERING_2013; break;
//                    case 2014 : this.TYPE = SMART_CAR_ENGINEERING_2014; break;
//                    case 2015 : this.TYPE = SMART_CAR_ENGINEERING_2015; break;
//                    case 2016 : this.TYPE = SMART_CAR_ENGINEERING_2016; break;
                    case 2017 : this.TYPE = SMART_CAR_ENGINEERING_2017; break;
                    case 2018 : this.TYPE = SMART_CAR_ENGINEERING_2018; break;
                }
                break;

            case "기계설계공학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = MECHANICAL_DESIGN_ENGINEERING_2012; break;
//                    case 2013 : this.TYPE = MECHANICAL_DESIGN_ENGINEERING_2013; break;
//                    case 2014 : this.TYPE = MECHANICAL_DESIGN_ENGINEERING_2014; break;
//                    case 2015 : this.TYPE = MECHANICAL_DESIGN_ENGINEERING_2015; break;
//                    case 2016 : this.TYPE = MECHANICAL_DESIGN_ENGINEERING_2016; break;
                    case 2017 : this.TYPE = MECHANICAL_DESIGN_ENGINEERING_2017; break;
                    case 2018 : this.TYPE = MECHANICAL_DESIGN_ENGINEERING_2018; break;
                }
                break;

            case "건축공학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = ARCHITECTURAL_ENGINEERING_2012; break;
//                    case 2013 : this.TYPE = ARCHITECTURAL_ENGINEERING_2013; break;
//                    case 2014 : this.TYPE = ARCHITECTURAL_ENGINEERING_2014; break;
//                    case 2015 : this.TYPE = ARCHITECTURAL_ENGINEERING_2015; break;
//                    case 2016 : this.TYPE = ARCHITECTURAL_ENGINEERING_2016; break;
                    case 2017 : this.TYPE = ARCHITECTURAL_ENGINEERING_2017; break;
                    case 2018 : this.TYPE = ARCHITECTURAL_ENGINEERING_2018; break;
                }
                break;

            case "도시공학부" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = URBAN_ENGINEERING_2012; break;
//                    case 2013 : this.TYPE = URBAN_ENGINEERING_2013; break;
//                    case 2014 : this.TYPE = URBAN_ENGINEERING_2014; break;
//                    case 2015 : this.TYPE = URBAN_ENGINEERING_2015; break;
//                    case 2016 : this.TYPE = URBAN_ENGINEERING_2016; break;
                    case 2017 : this.TYPE = URBAN_ENGINEERING_2017; break;
                    case 2018 : this.TYPE = URBAN_ENGINEERING_2018; break;
                }
                break;

            case "화학융합공학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = CHEMISTRY_CONVERGENCE_ENGINEERING_2012; break;
//                    case 2013 : this.TYPE = CHEMISTRY_CONVERGENCE_ENGINEERING_2013; break;
//                    case 2014 : this.TYPE = CHEMISTRY_CONVERGENCE_ENGINEERING_2014; break;
//                    case 2015 : this.TYPE = CHEMISTRY_CONVERGENCE_ENGINEERING_2015; break;
//                    case 2016 : this.TYPE = CHEMISTRY_CONVERGENCE_ENGINEERING_2016; break;
                    case 2017 : this.TYPE = CHEMISTRY_CONVERGENCE_ENGINEERING_2017; break;
                    case 2018 : this.TYPE = CHEMISTRY_CONVERGENCE_ENGINEERING_2018; break;
                }
                break;

            case "탄소융합공학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = CARBON_CONVERGENCE_ENGINEERING_2012; break;
//                    case 2013 : this.TYPE = CARBON_CONVERGENCE_ENGINEERING_2013; break;
//                    case 2014 : this.TYPE = CARBON_CONVERGENCE_ENGINEERING_2014; break;
//                    case 2015 : this.TYPE = CARBON_CONVERGENCE_ENGINEERING_2015; break;
//                    case 2016 : this.TYPE = CARBON_CONVERGENCE_ENGINEERING_2016; break;
                    case 2017 : this.TYPE = CARBON_CONVERGENCE_ENGINEERING_2017; break;
                    case 2018 : this.TYPE = CARBON_CONVERGENCE_ENGINEERING_2018; break;
                }
                break;

            case "건축학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = ARCHITECTURE_2012; break;
//                    case 2013 : this.TYPE = ARCHITECTURE_2013; break;
//                    case 2014 : this.TYPE = ARCHITECTURE_2014; break;
//                    case 2015 : this.TYPE = ARCHITECTURE_2015; break;
//                    case 2016 : this.TYPE = ARCHITECTURE_2016; break;
                    case 2017 : this.TYPE = ARCHITECTURE_2017; break;
                    case 2018 : this.TYPE = ARCHITECTURE_2018; break;
                }
                break;

            case "토목환경공학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = CIVIL_ENGINEERING_2012; break;
//                    case 2013 : this.TYPE = CIVIL_ENGINEERING_2013; break;
//                    case 2014 : this.TYPE = CIVIL_ENGINEERING_2014; break;
//                    case 2015 : this.TYPE = CIVIL_ENGINEERING_2015; break;
//                    case 2016 : this.TYPE = CIVIL_ENGINEERING_2016; break;
                    case 2017 : this.TYPE = CIVIL_ENGINEERING_2017; break;
                    case 2018 : this.TYPE = CIVIL_ENGINEERING_2018; break;
                }
                break;

            case "치의예과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = DENTISTRY_TREATMENT_2012; break;
//                    case 2013 : this.TYPE = DENTISTRY_TREATMENT_2013; break;
//                    case 2014 : this.TYPE = DENTISTRY_TREATMENT_2014; break;
//                    case 2015 : this.TYPE = DENTISTRY_TREATMENT_2015; break;
//                    case 2016 : this.TYPE = DENTISTRY_TREATMENT_2016; break;
                    case 2017 : this.TYPE = DENTISTRY_TREATMENT_2017; break;
                    case 2018 : this.TYPE = DENTISTRY_TREATMENT_2018; break;
                }
                break;

            case "치의학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = DENTISTRY_SCHOLARSHIP_2012; break;
//                    case 2013 : this.TYPE = DENTISTRY_SCHOLARSHIP_2013; break;
//                    case 2014 : this.TYPE = DENTISTRY_SCHOLARSHIP_2014; break;
//                    case 2015 : this.TYPE = DENTISTRY_SCHOLARSHIP_2015; break;
//                    case 2016 : this.TYPE = DENTISTRY_SCHOLARSHIP_2016; break;
                    case 2017 : this.TYPE = DENTISTRY_SCHOLARSHIP_2017; break;
                    case 2018 : this.TYPE = DENTISTRY_SCHOLARSHIP_2018; break;
                }
                break;

            case "의예과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = MEDICAL_TREATMENT_2012; break;
//                    case 2013 : this.TYPE = MEDICAL_TREATMENT_2013; break;
//                    case 2014 : this.TYPE = MEDICAL_TREATMENT_2014; break;
//                    case 2015 : this.TYPE = MEDICAL_TREATMENT_2015; break;
//                    case 2016 : this.TYPE = MEDICAL_TREATMENT_2016; break;
                    case 2017 : this.TYPE = MEDICAL_TREATMENT_2017; break;
                    case 2018 : this.TYPE = MEDICAL_TREATMENT_2018; break;
                }
                break;

            case "의학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = MEDICAL_SCHOLARSHIP_2012; break;
//                    case 2013 : this.TYPE = MEDICAL_SCHOLARSHIP_2013; break;
//                    case 2014 : this.TYPE = MEDICAL_SCHOLARSHIP_2014; break;
//                    case 2015 : this.TYPE = MEDICAL_SCHOLARSHIP_2015; break;
//                    case 2016 : this.TYPE = MEDICAL_SCHOLARSHIP_2016; break;
                    case 2017 : this.TYPE = MEDICAL_SCHOLARSHIP_2017; break;
                    case 2018 : this.TYPE = MEDICAL_SCHOLARSHIP_2018; break;
                }
                break;

            case "간호학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = NURSING_SCIENCE_2012; break;
//                    case 2013 : this.TYPE = NURSING_SCIENCE_2013; break;
//                    case 2014 : this.TYPE = NURSING_SCIENCE_2014; break;
//                    case 2015 : this.TYPE = NURSING_SCIENCE_2015; break;
//                    case 2016 : this.TYPE = NURSING_SCIENCE_2016; break;
                    case 2017 : this.TYPE = NURSING_SCIENCE_2017; break;
                    case 2018 : this.TYPE = NURSING_SCIENCE_2018; break;
                }
                break;

            case "작업치료학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = OCCUPATIONAL_THERAPY_2012; break;
//                    case 2013 : this.TYPE = OCCUPATIONAL_THERAPY_2013; break;
//                    case 2014 : this.TYPE = OCCUPATIONAL_THERAPY_2014; break;
//                    case 2015 : this.TYPE = OCCUPATIONAL_THERAPY_2015; break;
//                    case 2016 : this.TYPE = OCCUPATIONAL_THERAPY_2016; break;
                    case 2017 : this.TYPE = OCCUPATIONAL_THERAPY_2017; break;
                    case 2018 : this.TYPE = OCCUPATIONAL_THERAPY_2018; break;
                }
                break;

            case "약학과" :
                switch(studentNo) {
//                    case 2012 : this.TYPE = PHARMACY_2012; break;
//                    case 2013 : this.TYPE = PHARMACY_2013; break;
//                    case 2014 : this.TYPE = PHARMACY_2014; break;
//                    case 2015 : this.TYPE = PHARMACY_2015; break;
//                    case 2016 : this.TYPE = PHARMACY_2016; break;
                    case 2017 : this.TYPE = PHARMACY_2017; break;
                    case 2018 : this.TYPE = PHARMACY_2018; break;
                }
                break;
            default: this.TYPE = UNKOWN_TYPE; break;
        }
    }

    public int getType() { return this.TYPE; }

    public WKUGraduationData getWkuGraduationData() {
        return this.wkuGraduationData;
    }

    public ArrayList<WKUGraduationData> getWkuGraduationDatas() {
        return this.wkuGraduationDatas;
    }

    private void setCurGraduationCredit() {
        float graduationCredit = 0;

        Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT category, code, subject, credit, avgGrade, markGrade FROM wkuGradeDetailData", null);

        while(cursor.moveToNext()) {
            graduationCredit += cursor.getDouble(3);
            wkuGraduationData.getGraduationCreditLists().add(new WKUGraduationCompulsorySubjectData(cursor.getString(1), cursor.getString(0), (float)cursor.getDouble(3), cursor.getString(2)));
        }

        wkuGraduationData.setCurGraduationCredit(graduationCredit);
    }

    private void setCurMajorCredit() {
        float majorCredit = 0;

        Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT category, code, subject, credit, avgGrade, markGrade FROM wkuGradeDetailData", null);

        while(cursor.moveToNext()) {
            if(cursor.getString(0).equals("기전") || cursor.getString(0).equals("선전") || cursor.getString(0).equals("복수") || cursor.getString(0).equals("응전")) {
                majorCredit += cursor.getDouble(3);
                wkuGraduationData.getMajorCreditLists().add(new WKUGraduationCompulsorySubjectData(cursor.getString(1), cursor.getString(0), (float)cursor.getDouble(3),  cursor.getString(2)));
            }
        }

        wkuGraduationData.setCurMajorCredit(majorCredit);
    }

    private void setCurMajorBasisCredit() {
        float majorBasisCredit = 0;

        Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT category, code, subject, credit, avgGrade, markGrade FROM wkuGradeDetailData", null);

        while(cursor.moveToNext()) {
            if(cursor.getString(0).equals("기전")) {
                majorBasisCredit += cursor.getDouble(3);
                wkuGraduationData.getMajorBasisCreditLists().add(new WKUGraduationCompulsorySubjectData(cursor.getString(1), cursor.getString(0), (float)cursor.getDouble(3), cursor.getString(2)));
            }
        }

        wkuGraduationData.setCurMajorBasisCredit(majorBasisCredit);
    }

    private void setCurGeneralCredit() {
        float generalCredit = 0;

        Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT category, code, subject, credit, avgGrade, markGrade FROM wkuGradeDetailData", null);

        while(cursor.moveToNext()) {
            if(cursor.getString(0).equals("교필") || cursor.getString(0).equals("교선") || cursor.getString(0).equals("계필")) {
                generalCredit += cursor.getDouble(3);
                wkuGraduationData.getGeneralCreditLists().add(new WKUGraduationCompulsorySubjectData(cursor.getString(1), cursor.getString(0), (float)cursor.getDouble(3), cursor.getString(2)));
            }
        }

        wkuGraduationData.setCurGeneralCredit(generalCredit);
    }

    private void setCurGeneralAffiliationCredit() {
        float generalAffiliationCredit = 0;

        Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT category, code, subject, credit, avgGrade, markGrade FROM wkuGradeDetailData", null);

        while(cursor.moveToNext()) {
            if(cursor.getString(0).equals("계필")) {
                generalAffiliationCredit += cursor.getDouble(3);
                wkuGraduationData.getGeneralAffiliationCreditLists().add(new WKUGraduationCompulsorySubjectData(cursor.getString(1), cursor.getString(0), (float)cursor.getDouble(3), cursor.getString(2)));
            }
        }

        wkuGraduationData.setCurGeneralAffiliationCredit(generalAffiliationCredit);
    }
}
