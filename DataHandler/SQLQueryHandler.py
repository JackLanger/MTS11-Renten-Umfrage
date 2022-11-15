import pymssql


class SQLQueryHandler:
    def __init__(self, server, user, password, database):
        self.server = server
        self.user = user
        self.password = password
        self.database = database
        self.connection = pymssql.connect(server, user, password, database)
        self.cursor = self.connection.cursor()

    def __meta_to_list(self, data):
        return [x[0] for x in data]

    def meta_column_name(self, table):
        query = self.query(f"SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = N\'{table}\'")
        return self.__meta_to_list(query)

    def meta_column_type(self, table):
        query = self.query(f"SELECT DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = N\'{table}\'")
        return self.__meta_to_list(query)

    def meta_table_name(self):
        query = self.query("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES")
        return self.__meta_to_list(query)

    def get_primary_key(self, table):
        query = self.query(f"SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE OBJECTPROPERTY(OBJECT_ID(CONSTRAINT_SCHEMA + '.' + QUOTENAME(CONSTRAINT_NAME)), 'IsPrimaryKey') = 1 AND TABLE_NAME = '{table}'")
        return self.__meta_to_list(query)[0]

    def meta_foreign_keys(self, table):
        query = self.query(f"SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE OBJECTPROPERTY(OBJECT_ID(CONSTRAINT_SCHEMA + '.' + QUOTENAME(CONSTRAINT_NAME)), 'IsForeignKey') = 1 AND TABLE_NAME = '{table}'")
        return self.__meta_to_list(query)

    def query(self, query):
        self.cursor.execute(query)
        return self.cursor.fetchall()

    def close(self):
        self.connection.close()

    def insert(self, table, data):
        keys = ""
        values = ""
        first = True
        for key in data.keys():
            if not first:
                keys += ", "
                values += ", "
            keys += f"{key}"
            values += f"'{data[key]}'"
            if first:
                first = False
        self.cursor.execute(f"INSERT INTO {table} ({keys}) VALUES ({values})")
        self.connection.commit()

    def remove_quotation_marks(self, data):
        return data.replace('"', "")
