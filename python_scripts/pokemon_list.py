import csv
import requests
from bs4 import BeautifulSoup

proxies = {
    'http': '127.0.0.1:7890',
    'https': '127.0.0.1:7890'
}


def update_pokemon_list():
    url = "https://wiki.52poke.com/zh-hans/%E5%AE%9D%E5%8F%AF%E6%A2%A6%E5%88%97%E8%A1%A8%EF%BC%88%E6%8C%89%E5%85%A8" \
          "%E5%9B%BD%E5%9B%BE%E9%89%B4%E7%BC%96%E5%8F%B7%EF%BC%89 "
    r = requests.get(url, proxies=proxies)
    soup = BeautifulSoup(r.text, features="html.parser")

    tables = soup.find_all("table", "eplist")
    with open('pokemon.csv', 'w', newline='', encoding='UTF-8') as csv_file:
        pokemon_writer = csv.writer(csv_file, quoting=csv.QUOTE_NONNUMERIC)
        for pokemon_table in tables:
            pokemon_rows = pokemon_table.find_all('tr')
            for pokemon_row in pokemon_rows:
                tds = pokemon_row.find_all('td')
                if len(tds) > 0:
                    pokemon_num = tds[0].string.replace('#', '').replace("\n", '')
                    pokemon_name = tds[2].a.string
                    pokemon_type1 = tds[5].a.string
                    pokemon_type2 = ''
                    if tds[6].a is not None:
                        pokemon_type2 = tds[6].a.string
                    pokemon_writer.writerow([
                        pokemon_num, pokemon_name, pokemon_type1, pokemon_type2
                    ])


if __name__ == '__main__':
    update_pokemon_list()
