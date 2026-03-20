select t.tipoid as id, t.tipo as tipo, t.descripcion as descripcion, t.color as color, t.codeExtra as codeExtra from tipos t
left join Tipotipos tt on tt.tipotipoid = t.tipotipoid
where tt.sigla = '?1';