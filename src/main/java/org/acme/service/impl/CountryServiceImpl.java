package org.acme.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.service.CountryService;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class CountryServiceImpl implements CountryService {

    final private Map<String, String> ISOCODES = new HashMap<>();

    public CountryServiceImpl() {
        ISOCODES.put("AF", "Afghanistan");
        ISOCODES.put("AX", "Aland Islands");
        ISOCODES.put("AL", "Albania");
        ISOCODES.put("DZ", "Algeria");
        ISOCODES.put("AS", "American Samoa");
        ISOCODES.put("AD", "Andorra");
        ISOCODES.put("AI", "Anguilla");
        ISOCODES.put("AQ", "Antarctica");
        ISOCODES.put("AG", "Antigua And Barbuda");
        ISOCODES.put("AO", "Angola");
        ISOCODES.put("AR", "Argentina");
        ISOCODES.put("AM", "Armenia");
        ISOCODES.put("AW", "Aruba");
        ISOCODES.put("AU", "Australia");
        ISOCODES.put("AT", "Austria");
        ISOCODES.put("AZ", "Azerbaijan");
        ISOCODES.put("BS", "Bahamas");
        ISOCODES.put("BH", "Bahrain");
        ISOCODES.put("BD", "Bangladesh");
        ISOCODES.put("BB", "Barbados");
        ISOCODES.put("BY", "Belarus");
        ISOCODES.put("BE", "Belgium");
        ISOCODES.put("BZ", "Belize");
        ISOCODES.put("BJ", "Benin");
        ISOCODES.put("BM", "Bermuda");
        ISOCODES.put("BT", "Bhutan");
        ISOCODES.put("BO", "Bolivia");
        ISOCODES.put("BA", "Bosnia And Herzegovina");
        ISOCODES.put("BW", "Botswana");
        ISOCODES.put("BV", "Bouvet Island");
        ISOCODES.put("BR", "Brazil");
        ISOCODES.put("IO", "British Indian Ocean Territory");
        ISOCODES.put("BN", "Brunei Darussalam");
        ISOCODES.put("BG", "Bulgaria");
        ISOCODES.put("BF", "Burkina Faso");
        ISOCODES.put("BI", "Burundi");
        ISOCODES.put("KH", "Cambodia");
        ISOCODES.put("CM", "Cameroon");
        ISOCODES.put("CA", "Canada");
        ISOCODES.put("CV", "Cape Verde");
        ISOCODES.put("KY", "Cayman Islands");
        ISOCODES.put("CF", "Central African Republic");
        ISOCODES.put("TD", "Chad");
        ISOCODES.put("CL", "Chile");
        ISOCODES.put("CN", "China");
        ISOCODES.put("CX", "Christmas Island");
        ISOCODES.put("CC", "Cocos (Keeling) Islands");
        ISOCODES.put("CO", "Colombia");
        ISOCODES.put("KM", "Comoros");
        ISOCODES.put("CG", "Congo");
        ISOCODES.put("CD", "Congo, Democratic Republic");
        ISOCODES.put("CK", "Cook Islands");
        ISOCODES.put("CR", "Costa Rica");
        ISOCODES.put("CI", "Cote D Ivoire");
        ISOCODES.put("HR", "Croatia");
        ISOCODES.put("CU", "Cuba");
        ISOCODES.put("CY", "Cyprus");
        ISOCODES.put("CZ", "Czech Republic");
        ISOCODES.put("DK", "Denmark");
        ISOCODES.put("DJ", "Djibouti");
        ISOCODES.put("DM", "Dominica");
        ISOCODES.put("DO", "Dominican Republic");
        ISOCODES.put("EC", "Ecuador");
        ISOCODES.put("EG", "Egypt");
        ISOCODES.put("SV", "El Salvador");
        ISOCODES.put("GQ", "Equatorial Guinea");
        ISOCODES.put("ER", "Eritrea");
        ISOCODES.put("EE", "Estonia");
        ISOCODES.put("ET", "Ethiopia");
        ISOCODES.put("FK", "Falkland Islands (Malvinas)");
        ISOCODES.put("FO", "Faroe Islands");
        ISOCODES.put("FJ", "Fiji");
        ISOCODES.put("FI", "Finland");
        ISOCODES.put("FR", "France");
        ISOCODES.put("GF", "French Guiana");
        ISOCODES.put("PF", "French Polynesia");
        ISOCODES.put("TF", "French Southern Territories");
        ISOCODES.put("GA", "Gabon");
        ISOCODES.put("GM", "Gambia");
        ISOCODES.put("GE", "Georgia");
        ISOCODES.put("DE", "Germany");
        ISOCODES.put("GH", "Ghana");
        ISOCODES.put("GI", "Gibraltar");
        ISOCODES.put("GR", "Greece");
        ISOCODES.put("GL", "Greenland");
        ISOCODES.put("GD", "Grenada");
        ISOCODES.put("GP", "Guadeloupe");
        ISOCODES.put("GU", "Guam");
        ISOCODES.put("GT", "Guatemala");
        ISOCODES.put("GG", "Guernsey");
        ISOCODES.put("GN", "Guinea");
        ISOCODES.put("GW", "Guinea-Bissau");
        ISOCODES.put("GY", "Guyana");
        ISOCODES.put("HT", "Haiti");
        ISOCODES.put("HM", "Heard Island & Mcdonald Islands");
        ISOCODES.put("VA", "Holy See (Vatican City State)");
        ISOCODES.put("HN", "Honduras");
        ISOCODES.put("HK", "Hong Kong");
        ISOCODES.put("HU", "Hungary");
        ISOCODES.put("IS", "Iceland");
        ISOCODES.put("IN", "India");
        ISOCODES.put("ID", "Indonesia");
        ISOCODES.put("IR", "Iran, Islamic Republic Of");
        ISOCODES.put("IQ", "Iraq");
        ISOCODES.put("IE", "Ireland");
        ISOCODES.put("IM", "Isle Of Man");
        ISOCODES.put("IL", "Israel");
        ISOCODES.put("IT", "Italy");
        ISOCODES.put("JM", "Jamaica");
        ISOCODES.put("JP", "Japan");
        ISOCODES.put("JE", "Jersey");
        ISOCODES.put("JO", "Jordan");
        ISOCODES.put("KZ", "Kazakhstan");
        ISOCODES.put("KE", "Kenya");
        ISOCODES.put("KI", "Kiribati");
        ISOCODES.put("KR", "Korea");
        ISOCODES.put("KP", "North Korea");
        ISOCODES.put("KW", "Kuwait");
        ISOCODES.put("KG", "Kyrgyzstan");
        ISOCODES.put("LA", "Lao People s Democratic Republic");
        ISOCODES.put("LV", "Latvia");
        ISOCODES.put("LB", "Lebanon");
        ISOCODES.put("LS", "Lesotho");
        ISOCODES.put("LR", "Liberia");
        ISOCODES.put("LY", "Libyan Arab Jamahiriya");
        ISOCODES.put("LI", "Liechtenstein");
        ISOCODES.put("LT", "Lithuania");
        ISOCODES.put("LU", "Luxembourg");
        ISOCODES.put("MO", "Macao");
        ISOCODES.put("MK", "Macedonia");
        ISOCODES.put("MG", "Madagascar");
        ISOCODES.put("MW", "Malawi");
        ISOCODES.put("MY", "Malaysia");
        ISOCODES.put("MV", "Maldives");
        ISOCODES.put("ML", "Mali");
        ISOCODES.put("MT", "Malta");
        ISOCODES.put("MH", "Marshall Islands");
        ISOCODES.put("MQ", "Martinique");
        ISOCODES.put("MR", "Mauritania");
        ISOCODES.put("MU", "Mauritius");
        ISOCODES.put("YT", "Mayotte");
        ISOCODES.put("MX", "Mexico");
        ISOCODES.put("FM", "Micronesia, Federated States Of");
        ISOCODES.put("MD", "Moldova");
        ISOCODES.put("MC", "Monaco");
        ISOCODES.put("MN", "Mongolia");
        ISOCODES.put("ME", "Montenegro");
        ISOCODES.put("MS", "Montserrat");
        ISOCODES.put("MA", "Morocco");
        ISOCODES.put("MZ", "Mozambique");
        ISOCODES.put("MM", "Myanmar");
        ISOCODES.put("NA", "Namibia");
        ISOCODES.put("NR", "Nauru");
        ISOCODES.put("NP", "Nepal");
        ISOCODES.put("NL", "Netherlands");
        ISOCODES.put("AN", "Netherlands Antilles");
        ISOCODES.put("NC", "New Caledonia");
        ISOCODES.put("NZ", "New Zealand");
        ISOCODES.put("NI", "Nicaragua");
        ISOCODES.put("NE", "Niger");
        ISOCODES.put("NG", "Nigeria");
        ISOCODES.put("NU", "Niue");
        ISOCODES.put("NF", "Norfolk Island");
        ISOCODES.put("MP", "Northern Mariana Islands");
        ISOCODES.put("NO", "Norway");
        ISOCODES.put("OM", "Oman");
        ISOCODES.put("PK", "Pakistan");
        ISOCODES.put("PW", "Palau");
        ISOCODES.put("PS", "Palestinian Territory, Occupied");
        ISOCODES.put("PA", "Panama");
        ISOCODES.put("PG", "Papua New Guinea");
        ISOCODES.put("PY", "Paraguay");
        ISOCODES.put("PE", "Peru");
        ISOCODES.put("PH", "Philippines");
        ISOCODES.put("PN", "Pitcairn");
        ISOCODES.put("PL", "Poland");
        ISOCODES.put("PT", "Portugal");
        ISOCODES.put("PR", "Puerto Rico");
        ISOCODES.put("QA", "Qatar");
        ISOCODES.put("RE", "Reunion");
        ISOCODES.put("RO", "Romania");
        ISOCODES.put("RU", "Russian Federation");
        ISOCODES.put("RW", "Rwanda");
        ISOCODES.put("BL", "Saint Barthelemy");
        ISOCODES.put("SH", "Saint Helena");
        ISOCODES.put("KN", "Saint Kitts And Nevis");
        ISOCODES.put("LC", "Saint Lucia");
        ISOCODES.put("MF", "Saint Martin");
        ISOCODES.put("PM", "Saint Pierre And Miquelon");
        ISOCODES.put("VC", "Saint Vincent And Grenadines");
        ISOCODES.put("WS", "Samoa");
        ISOCODES.put("SM", "San Marino");
        ISOCODES.put("ST", "Sao Tome And Principe");
        ISOCODES.put("SA", "Saudi Arabia");
        ISOCODES.put("SN", "Senegal");
        ISOCODES.put("RS", "Serbia");
        ISOCODES.put("SC", "Seychelles");
        ISOCODES.put("SL", "Sierra Leone");
        ISOCODES.put("SG", "Singapore");
        ISOCODES.put("SK", "Slovakia");
        ISOCODES.put("SI", "Slovenia");
        ISOCODES.put("SB", "Solomon Islands");
        ISOCODES.put("SO", "Somalia");
        ISOCODES.put("ZA", "South Africa");
        ISOCODES.put("GS", "South Georgia And Sandwich Isl.");
        ISOCODES.put("ES", "Spain");
        ISOCODES.put("LK", "Sri Lanka");
        ISOCODES.put("SD", "Sudan");
        ISOCODES.put("SR", "Suriname");
        ISOCODES.put("SJ", "Svalbard And Jan Mayen");
        ISOCODES.put("SZ", "Swaziland");
        ISOCODES.put("SE", "Sweden");
        ISOCODES.put("CH", "Switzerland");
        ISOCODES.put("SY", "Syrian Arab Republic");
        ISOCODES.put("TW", "Taiwan");
        ISOCODES.put("TJ", "Tajikistan");
        ISOCODES.put("TZ", "Tanzania");
        ISOCODES.put("TH", "Thailand");
        ISOCODES.put("TL", "Timor-Leste");
        ISOCODES.put("TG", "Togo");
        ISOCODES.put("TK", "Tokelau");
        ISOCODES.put("TO", "Tonga");
        ISOCODES.put("TT", "Trinidad And Tobago");
        ISOCODES.put("TN", "Tunisia");
        ISOCODES.put("TR", "Turkey");
        ISOCODES.put("TM", "Turkmenistan");
        ISOCODES.put("TC", "Turks And Caicos Islands");
        ISOCODES.put("TV", "Tuvalu");
        ISOCODES.put("UG", "Uganda");
        ISOCODES.put("UA", "Ukraine");
        ISOCODES.put("AE", "United Arab Emirates");
        ISOCODES.put("GB", "United Kingdom");
        ISOCODES.put("US", "United States");
        ISOCODES.put("UM", "United States Outlying Islands");
        ISOCODES.put("UY", "Uruguay");
        ISOCODES.put("UZ", "Uzbekistan");
        ISOCODES.put("VU", "Vanuatu");
        ISOCODES.put("VE", "Venezuela");
        ISOCODES.put("VN", "Vietnam");
        ISOCODES.put("VG", "Virgin Islands, British");
        ISOCODES.put("VI", "Virgin Islands, U.S.");
        ISOCODES.put("WF", "Wallis And Futuna");
        ISOCODES.put("EH", "Western Sahara");
        ISOCODES.put("YE", "Yemen");
        ISOCODES.put("ZM", "Zambia");
        ISOCODES.put("ZW", "Zimbabwe");
    }

    /**
     * @param isoCode - IS0 3166 for country
     * @return if a valid country ISO 3166 code
     */
    @Override
    public Boolean isValid(String isoCode) {
        if (isoCode == null || isoCode.isBlank()) {
            return true;
        }
        return ISOCODES.containsKey(isoCode);
    }
}
