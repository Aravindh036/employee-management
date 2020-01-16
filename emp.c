#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <libpq-fe.h>

PGconn *conn;
PGresult *res;

struct employee
{
    char name[10];
    int id;
    int age;
    char designation[10];
    long salary;
    struct employee *next;
    struct employee *prev;
};

void do_exit(PGconn *conn, PGresult *res)
{

    fprintf(stderr, "%s\n", PQerrorMessage(conn));

    PQclear(res);
    PQfinish(conn);

    exit(1);
}

void addRecordToDB(struct employee *node)
{

    char name[15];
    strcpy(name, node->name);
    char id[10];
    sprintf(id, "%d", node->id);
    char age[3];
    sprintf(age, "%d", node->age);
    char designation[15];
    strcpy(designation, node->designation);
    char salary[100];
    sprintf(salary, "%ld", node->salary);
    const char *arr[5];
    arr[0] = id;
    arr[1] = name;
    arr[2] = age;
    arr[3] = designation;
    arr[4] = salary;

    res = PQexecParams(conn, "INSERT INTO Employee VALUES($1,$2,$3,$4,$5)", 5, NULL, arr, NULL, NULL, 0);
    if (PQresultStatus(res) != PGRES_COMMAND_OK)
    {
        do_exit(conn, res);
    }
}


int searchRecord(struct employee *search, int id)
{
    int found = 0;

    while ((search != NULL) && (search->id != 0))
    {
        if (search->id == id)
        {
            found = 1;
            break;
        }
        search = search->next;
    }
    return found;
}

int listRecord(struct employee *list)
{
    int found = 0;
    while ((list != NULL) && (list->id != 0))
    {
        found = 1;
        printf("\nName : %s\t ID: %d\n", list->name, list->id);
        list = list->next;
    }
    return found;
}

void printNode(struct employee *node)
{
    printf("%s\t\t%d\t\t%d\t\t%s\t\t\t%ld\n", node->name, node->id, node->age, node->designation, node->salary);
}

void swapId(struct employee *a, struct employee *b,struct employee* start)
{
    struct employee *temp = (struct employee *)malloc(sizeof(struct employee));
    temp->id = a->id;
    temp->age = a->age;
    temp->salary = a->salary;
    strcpy(temp->designation, a->designation);
    strcpy(temp->name, a->name);
    a->id = b->id;
    a->age = b->age;
    a->salary = b->salary;
    strcpy(a->designation, b->designation);
    strcpy(a->name, b->name);
    b->id = temp->id;
    b->age = temp->age;
    b->salary = temp->salary;
    strcpy(b->designation, temp->designation);
    strcpy(b->name, temp->name);
    free(temp);
}

void sortList(struct employee *node)
{
    int swap;
    struct employee *current;
    struct employee *fixed = NULL;

    if (node == NULL)
        return;

    do
    {
        swap = 0;
        current = node;
        while (current->next != fixed)
        {
            if (current->id > current->next->id)
            {
                swapId(current, current->next,node);
                swap = 1;
            }
            current = current->next;
        }
        fixed = current;
    } while (swap);
}

struct employee *getAllRecords(struct employee *start)
{
    res = PQexec(conn, "SELECT * from Employee");
    int rows = PQntuples(res);
    for (int i = 0; i < rows; i++)
    {
        struct employee *temp = (struct employee *)malloc(sizeof(struct employee));

        if (start == NULL)
        {
            start = temp;
            start->prev = NULL;
            start->next = NULL;
        }
        else
        {
            start->next = temp;
            temp->prev = start;
            temp->next = NULL;
            start = temp;
        }
        sscanf(PQgetvalue(res, i, 0), "%d", &start->id);
        sscanf(PQgetvalue(res, i, 2), "%d", &start->age);
        sscanf(PQgetvalue(res, i, 4), "%d", &start->salary);
        strcpy(start->name, PQgetvalue(res, i, 1));
        strcpy(start->designation, PQgetvalue(res, i, 3));
    }
    return start;
}
void update(struct employee *node, int edit_id)
{
    char name[15];
    strcpy(name, node->name);
    char id[10];
    sprintf(id, "%d", node->id);
    char age[3];
    sprintf(age, "%d", node->age);
    char designation[15];
    strcpy(designation, node->designation);
    char salary[100];
    sprintf(salary, "%ld", node->salary);
    char string_id[10];
    sprintf(string_id, "%d", edit_id);
    const char *arr[6];
    arr[0] = id;
    arr[1] = name;
    arr[2] = age;
    arr[3] = designation;
    arr[4] = salary;
    arr[5] = string_id;
    res = PQexecParams(conn, "UPDATE employee SET id=$1, name=$2, age=$3, designation=$4, salary=$5 WHERE id=$6", 6, NULL, arr, NULL, NULL, 0);
    if (PQresultStatus(res) != PGRES_COMMAND_OK)
    {
        do_exit(conn, res);
    }
}

void deleteRecord(int id)
{
    char delete_id[10];
    sprintf(delete_id, "%d", id);
    const char *arr[1];
    arr[0] = delete_id;
    res = PQexecParams(conn, "DELETE from employee where id=$1", 1, NULL, arr, NULL, NULL, 0);
}
int main()
{
    system("cls");
    conn = PQconnectdb("user=postgres password=6279and77@$ dbname=postgres");
    res = PQexec(conn, "CREATE TABLE Employee(Id INTEGER PRIMARY KEY, Name VARCHAR(15), Age INT, Designation VARCHAR(15), Salary INT)");
    struct employee *head = NULL;
    struct employee *head_constant = NULL;
    int CURR = 0;
    int id, temp_curr = 0, temp_data_int, record_found = 0;
    char temp_data_char[10];
    int temp_id;
    // ReadList(head_constant);
    head = getAllRecords(head_constant);
    head_constant = head;
    if (head_constant != NULL)
    {
        while (head_constant->prev != NULL)
        {
            head_constant = head_constant->prev;
        }
    }
    sortList(head_constant);
    while (1)
    {
        int ch;
        printf("\n\n1.Add an Employee\n2.Edit Employee Details\n3.Delete Employee Details\n4.View an Employee's Details\n5.View all Employee's Details\n6.Exit\n");
        scanf("%d", &ch);
        switch (ch)
        {
        case 1:
            printf("\nEmployee ID: ");
            scanf("%d", &temp_id);
            if (searchRecord(head_constant, temp_id))
            {
                printf("\nAn Employee with this ID already exist, %d\n", temp_id);
                break;
            }
            struct employee *next = (struct employee *)malloc(sizeof(struct employee));
            if (head_constant == NULL)
            {
                head_constant = next;
                head_constant->next = NULL;
                head_constant->prev = NULL;
                head = head_constant;
            }
            else
            {
                head->next = next;
                next->prev = head;
                head = next;
                head->next = NULL;
            }
            head->id = temp_id;
            printf("\nEmployee name: ");
            scanf(" %[^\n]s", &head->name);
            printf("\nEmployee age: ");
            scanf(" %d", &head->age);
            printf("\nDesignation: ");
            scanf(" %[^\n]s", head->designation);
            printf("\nSalary: ");
            scanf(" %ld", &head->salary);
            // writeFile(head);
            addRecordToDB(head);
            sortList(head_constant);
            break;
        case 2:
            temp_curr = 0;
            struct employee *edit_temp = NULL;
            char enter;
            if (!listRecord(head_constant))
            {
                printf("\nNo records found to edit!\n");
                break;
            }
            printf("\nEmployee ID: ");
            scanf("%d", &id);
            edit_temp = head_constant;
            record_found = 0;
            while ((edit_temp != NULL) && (edit_temp->id != 0))
            {
                if (edit_temp->id == id)
                {
                    record_found = 1;
                    printf("\nEdit Employee name [y/n]: ");
                    scanf(" %c", &enter);
                    if (enter == 'y')
                    {
                        scanf(" %[^\n]s", edit_temp->name);
                    }
                    printf("\nEdit Employee ID [y/n]: ");
                    scanf(" %c", &enter);
                    if (enter == 'y')
                    {
                        scanf("%d", &edit_temp->id);
                    }
                    printf("\nEdit Employee Age [y/n]: ");
                    scanf(" %c", &enter);
                    if (enter == 'y')
                    {
                        scanf("%d", &edit_temp->age);
                    }
                    printf("\nEdit Employee Designation [y/n]: ");
                    scanf(" %c", &enter);
                    if (enter == 'y')
                    {
                        scanf("%s", edit_temp->designation);
                    }
                    printf("\nEdit Employee Salary [y/n]: ");
                    scanf(" %c", &enter);
                    if (enter == 'y')
                    {
                        scanf("%ld", &edit_temp->salary);
                    }
                    printf("\nRecord updated successfully");
                    // FILE *edit_file;
                    // edit_file = fopen("employee.bin", "w");
                    // fclose(edit_file);
                    // edit_temp = head_constant;
                    // while (edit_temp->next != NULL)
                    // {
                    //     writeFile(edit_temp);
                    //     edit_temp = edit_temp->next;
                    // }
                    update(edit_temp, id);
                    sortList(head_constant);
                    break;
                }
                edit_temp = edit_temp->next;
            }

            if (record_found == 0)
            {
                printf("\nNo record found for the ID\n");
            }
            break;
        case 3:
            printf("\n");

            struct employee *delete_temp = NULL;
            delete_temp = head_constant;
            record_found = 0;
            if (!listRecord(head_constant))
            {
                printf("\nNo records are found!!\n");
                break;
            }
            delete_temp = head_constant;
            printf("Enter the ID: ");
            scanf("%d", &id);
            record_found = 0;
            while ((delete_temp != NULL) && (delete_temp->id != 0))
            {
                if (delete_temp->id == id)
                {
                    record_found = 1;
                    free(head_constant);
                    head_constant = head_constant->next;
                    deleteRecord(id);
                    printf("\nSuccessfully Deleted!!\n");
                    // sortList(head_constant);
                    break;
                }
                else if (delete_temp->next->id == id)
                {
                    record_found = 1;
                    free(delete_temp->next);
                    delete_temp->next = delete_temp->next->next;
                    deleteRecord(id);
                    printf("\nSuccessfully Deleted!!\n");
                    // sortList(head_constant);
                    break;
                }
                delete_temp = delete_temp->next;
            }
            if (record_found == 0)
            {
                printf("\nEntered ID not found\n");
            }
            break;
        case 4:
            temp_curr = 0;
            record_found = 0;
            struct employee *view_temp = NULL;
            int search_id;
            if (!listRecord(head_constant))
            {
                printf("\nNo record found!!\n");
                break;
            }
            printf("\nSearch the Employee record by ID [y/n]:");
            char input;
            scanf(" %c", &input);
            record_found = 0;
            if (input == 'y')
            {
                printf("\nEnter the ID : ");
                scanf("%d", &search_id);
                view_temp = head_constant;
                // view_temp = view_temp->next;
                while ((view_temp != NULL) && (view_temp->id != 0))
                {
                    if (view_temp->id == search_id)
                    {
                        record_found = 1;
                        printf("Name\t\tID\t\tAge\t\tDesignation\t\tSalary\n");
                        printNode(view_temp);
                        break;
                    }
                    view_temp = view_temp->next;
                }
                if (record_found == 0)
                {
                    printf("\n No record found for the ID");
                }
                break;
            }
            printf("\nSearch the Employee record by name [y/n]:");
            scanf(" %c", &input);
            char search_name[10];
            if (input == 'y')
            {
                printf("\nEnter the Name : ");
                scanf(" %[^\n]s", &search_name);
                view_temp = head_constant;
                while ((view_temp != NULL) && (view_temp->id != 0))
                {
                    if (!strcmp(view_temp->name, search_name))
                    {
                        record_found = 1;
                        printf("Name\t\tID\t\tAge\t\tDesignation\t\tSalary\n");
                        printNode(view_temp);
                        break;
                    }
                    view_temp = view_temp->next;
                }
            }
            else
            {
                break;
            }
            if (record_found == 0)
            {
                printf("\nNo record found for the ID\n");
            }
            break;
        case 5:
            printf("\n");
            struct employee *list = NULL;
            list = head_constant;
            if (list == NULL)
            {
                printf("\nNo Records found\n");
            }
            else
            {
                printf("Name\t\tID\t\tAge\t\tDesignation\t\tSalary\n");
            }
            while ((list != NULL) && (list->id != 0))
            {
                printNode(list);
                list = list->next;
            }

            break;
        default:
            exit(0);
        }
    }
}
