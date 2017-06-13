package sample;

import java.util.ArrayList;

public class CityCoins {
    ArrayList<String> output = new ArrayList<String>();

    CityGrid[][] cityGrid = new CityGrid[25][25];

    public void setCities(String cities_strings) {
        ArrayList<String> input = new ArrayList<String>();

        for (String s : cities_strings.split("\n")) {
            input.add(s);
        }

        for(int i=0, index=0; input.get(i).charAt(0)!='0';i++,index++){
            String[] s = new String[Integer.parseInt(input.get(i))];
            for(int j=0;j<s.length;j++, i++){
                s[j] = input.get(i+1);
                System.out.println(s[j]);
            }
            makeCoinsMovement(s,index);

            System.out.println("\n");
        }
    }

    public void makeCoinsMovement(String[] s, int index){
        Country[] countries = new Country[s.length];
        for(int i=0; i< s.length; i++){
            String[] _tmp = s[i].split(" ");

            countries[i] = new Country();
            countries[i].name = _tmp[0];
            countries[i].xl   = Integer.parseInt(_tmp[1]);
            countries[i].yl   = Integer.parseInt(_tmp[2]);
            countries[i].xh   = Integer.parseInt(_tmp[3]);
            countries[i].yh   = Integer.parseInt(_tmp[4]);
        }
        createCountry(countries);
        checkCountriesFilled();

        int i=0;
        for(; !checkCountriesFilled(); i++){
            sendCoins();
        }
        output.add("Case Number "+(index+1));
        output.add(""+i);

    }

    public void createCountry(Country[] countries){

        for(int i=0; i<25;i++)
            for(int j=0; j<25; j++)
                cityGrid[i][j] = new CityGrid();

        for(int n = 0; n<countries.length; n++){
            for(int i=countries[n].xl; i<=countries[n].xh; i++)
                for(int j=countries[n].yl; j<=countries[n].yh; j++){
                    cityGrid[i][j].countryName = countries[n].name;
                    cityGrid[i][j].coins = new Coins[countries.length];
                    //cityGrid[i][j].coins = new int[countries.length];
                    for(int m=0; m<countries.length; m++)
                        if(m==n)
                        {
                            cityGrid[i][j].coins[m] = new Coins();
                            cityGrid[i][j].coins[m].count = 1000000;
                            cityGrid[i][j].coins[m].countryName = countries[m].name;
                        }
                        else
                        {
                            cityGrid[i][j].coins[m] = new Coins();
                            cityGrid[i][j].coins[m].count = 0;
                            cityGrid[i][j].coins[m].countryName = countries[m].name;
                        }
                    cityGrid[i][j].itUsed = true;
                }
        }
    }

    public boolean checkCountriesFilled(){
        for(CityGrid ci[]: cityGrid)
            for(CityGrid c: ci)
                if(c.itUsed)
                    for(Coins i: c.coins)
                        if(i.count == 0)
                            return false;
        return true;
    }

    private void sendCoins(){
        calculateTransactionAmount();
        makeTransaction();
    }

    private void calculateTransactionAmount(){
        for(CityGrid ci[]: cityGrid)
            for(CityGrid c: ci)
                if(c.itUsed)
                    for(int i=0; i<c.coins.length; i++)
                        c.coins[i].transaction_amount = c.coins[i].count/1000;
    }

    private void makeTransaction(){
        for(int i=0; i<cityGrid.length; i++)
            for(int j=0; j<cityGrid[i].length; j++)
                if(cityGrid[i][j].itUsed){

                    if(cityGrid[i][j-1].itUsed){
                        for(int n=0; n<cityGrid[i][j].coins.length; n++)
                        {
                            cityGrid[i][j].coins[n].count -= cityGrid[i][j].coins[n].transaction_amount;
                            cityGrid[i][j-1].coins[n].count += cityGrid[i][j].coins[n].transaction_amount;
                        }
                    }

                    if(cityGrid[i][j+1].itUsed){
                        for(int n=0; n<cityGrid[i][j].coins.length; n++)
                        {
                            cityGrid[i][j].coins[n].count -= cityGrid[i][j].coins[n].transaction_amount;
                            cityGrid[i][j+1].coins[n].count += cityGrid[i][j].coins[n].transaction_amount;
                        }
                    }

                    if(cityGrid[i-1][j].itUsed){
                        for(int n=0; n<cityGrid[i][j].coins.length; n++)
                        {
                            cityGrid[i][j].coins[n].count -= cityGrid[i][j].coins[n].transaction_amount;
                            cityGrid[i-1][j].coins[n].count += cityGrid[i][j].coins[n].transaction_amount;
                        }
                    }

                    if(cityGrid[i+1][j].itUsed){
                        for(int n=0; n<cityGrid[i][j].coins.length; n++)
                        {
                            cityGrid[i][j].coins[n].count -= cityGrid[i][j].coins[n].transaction_amount;
                            cityGrid[i+1][j].coins[n].count += cityGrid[i][j].coins[n].transaction_amount;
                        }
                    }
                }
    }
}
