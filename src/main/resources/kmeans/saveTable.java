/*
  Copiar y usar donde corresponda
*/
    public void saveTable(Table t, String filename) throws IOException {
        try {
            FileWriter fw = new FileWriter(filename);
            for (int i=0; i<t.getRowCount(); i++)
            {
                Row row = t.getRowAt(i);
                List<Double> datos = row.getData();
                int j=0;
                for (; j<datos.size()-1; j++)
                {
                    fw.write(datos.get(j).toString());
                    fw.write(",");
                }
                fw.write(datos.get(j).toString());
                fw.write("\n");
            }
            fw.close();
        } catch (IOException e) {
            throw e;
        }
    }

/*
  Posible contexto de uso
*/

/*
  El método ya está previsto para el sistema de recomendación, que implementaremos en las sesiones 2 y 3 de la práctica 2. Si quieres probarlo inmediatamente con kMeans, puedes cambiar "Algorithm<Table, Integer, Double> algorithm" por "kMeans algorithm".
 */
    public void savePredictions(Algorithm<Table, Integer, Double> algorithm, Table datos, String fileName) {
        Table datos_out = new Table();
        for (int i=0; i<datos.getRowCount(); i++) {
            List<Double> data = new ArrayList(datos.getRowAt(i).getData());
            double estimation = algorithm.estimate(data).doubleValue();
            data.add(estimation);
            datos_out.addRow(new Row(data));
        }
        try {
            saveTable(datos_out, fileName + "_out.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
